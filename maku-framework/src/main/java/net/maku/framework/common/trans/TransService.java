package net.maku.framework.common.trans;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Translation service
 * <p>
 * Provides manual batch translation for scenarios such as Excel export that need active translation
 *
 * @author maku
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransService {
    private final DictionaryTransService dictionaryTransService;

    /**
     * Batch translation
     *
     * @param dataList Data list to translate
     */
    public <T> void transBatch(List<T> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            return;
        }

        for (T data : dataList) {
            trans(data);
        }
    }

    /**
     * Single object translation
     *
     * @param data Object to translate
     */
    public void trans(Object data) {
        if (data == null) {
            return;
        }

        Class<?> clazz = data.getClass();
        List<Field> fields = TransReflectUtils.getAnnotationField(clazz, Trans.class);

        for (Field field : fields) {
            try {
                Trans trans = field.getAnnotation(Trans.class);
                if (trans == null) {
                    continue;
                }

                if (TransType.DICTIONARY.equals(trans.type())) {
                    translateDictionary(data, clazz, field, trans);
                } else if (TransType.SIMPLE.equals(trans.type())) {
                    translateSimple(data, clazz, field, trans);
                }
                // Other translation types can be extended here
            } catch (Exception e) {
                log.debug("Translation exception: field={}", field.getName(), e);
            }
        }
    }

    /**
     * Dictionary translation
     */
    private void translateDictionary(Object obj, Class<?> clazz, Field field, Trans trans) throws Exception {
        if (trans.key().isEmpty() || trans.ref().isEmpty()) {
            return;
        }

        field.setAccessible(true);
        Object fieldValue = field.get(obj);
        if (fieldValue == null) {
            return;
        }

        String label = dictionaryTransService.translate(trans.key(), fieldValue);
        if (label != null) {
            Field refField = TransReflectUtils.getDeclaredField(clazz, trans.ref());
            if (refField != null) {
                refField.setAccessible(true);
                refField.set(obj, label);
            }
        }
    }

    /**
     * Simple translation (looked up via the entity class)
     */
    @SuppressWarnings({"rawtypes"})
    private void translateSimple(Object obj, Class<?> clazz, Field field, Trans trans) throws Exception {
        if (trans.target() == Object.class || trans.fields().isEmpty() || trans.ref().isEmpty()) {
            return;
        }

        field.setAccessible(true);
        Object fieldValue = field.get(obj);
        if (fieldValue == null) {
            return;
        }

        try {
            // Get the Mapper corresponding to the target entity
            BaseMapper mapper = getMapper(trans.target());
            if (mapper == null) {
                return;
            }

            // Query target entity
            Object targetEntity = mapper.selectById((java.io.Serializable) fieldValue);
            if (targetEntity == null) {
                return;
            }

            // Get the value of the target field
            Field targetField = TransReflectUtils.getDeclaredField(trans.target(), trans.fields());
            if (targetField == null) {
                return;
            }
            targetField.setAccessible(true);
            Object targetValue = targetField.get(targetEntity);

            // Set to the ref field
            Field refField = TransReflectUtils.getDeclaredField(clazz, trans.ref());
            if (refField != null) {
                refField.setAccessible(true);
                refField.set(obj, targetValue);
            }
        } catch (Exception e) {
            log.debug("SimpleTranslation exception: target={}, field={}", trans.target().getSimpleName(), field.getName(), e);
        }
    }

    private final Map<Class<?>, BaseMapper<?>> mapperCache = new ConcurrentHashMap<>();

    @SuppressWarnings("rawtypes")
    private BaseMapper getMapper(Class<?> entityClass) {
        return mapperCache.computeIfAbsent(entityClass, c -> {
            try {
                Map<String, BaseMapper> mappers = SpringUtil.getBeansOfType(BaseMapper.class);
                for (BaseMapper mapper : mappers.values()) {
                    try {
                        String entityName = c.getSimpleName().replace("Entity", "");
                        for (Class<?> intf : mapper.getClass().getInterfaces()) {
                            if (intf.getSimpleName().contains(entityName)) {
                                return mapper;
                            }
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }
            } catch (Exception e) {
                log.debug("Failed to get Mapper: entityClass={}", entityClass.getSimpleName(), e);
            }
            return null;
        });
    }
}
