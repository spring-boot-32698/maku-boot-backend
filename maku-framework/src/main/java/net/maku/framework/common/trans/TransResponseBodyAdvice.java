package net.maku.framework.common.trans;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Global translation interceptor
 * <p>
 * Only intercept ResponseBody whose return value contains TransPojo, and automatically translate fields of objects implementing the TransPojo interface
 *
 * @author maku
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class TransResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private final TransService transService;

    /**
     * Cache the result of whether a method return type involves TransPojo, to avoid repeated reflection
     */
    private final Map<MethodParameter, Boolean> supportsCache = new ConcurrentHashMap<>();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return supportsCache.computeIfAbsent(returnType, this::containsTransPojo);
    }

    /**
     * Check whether the method return type directly or indirectly contains TransPojo
     */
    private boolean containsTransPojo(MethodParameter returnType) {
        Type genericType = returnType.getGenericParameterType();
        return checkType(genericType, new HashSet<>());
    }

    /**
     * Recursively check whether the type and its generic parameters involve TransPojo
     */
    private boolean checkType(Type type, Set<Type> visited) {
        if (type == null || !visited.add(type)) {
            return false;
        }

        // Handle Class type
        if (type instanceof Class<?> clazz) {
            if (TransPojo.class.isAssignableFrom(clazz)) {
                return true;
            }
            // Check whether the fields of the class contain the TransPojo type (only non-JDK classes are checked)
            if (!clazz.getName().startsWith("java.") && !clazz.getName().startsWith("javax.")
                    && !clazz.isPrimitive() && !clazz.isEnum()) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (checkType(field.getGenericType(), visited)) {
                        return true;
                    }
                }
            }
            return false;
        }

        // Handle generic parameter types, such as Result<PageResult<UserVO>>
        if (type instanceof ParameterizedType parameterizedType) {
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class<?> rawClass && TransPojo.class.isAssignableFrom(rawClass)) {
                return true;
            }
            for (Type arg : parameterizedType.getActualTypeArguments()) {
                if (checkType(arg, visited)) {
                    return true;
                }
            }
            // Also check fields of primitive types
            if (rawType instanceof Class<?>) {
                return checkType(rawType, visited);
            }
        }

        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        try {
            processTranslation(body);
        } catch (Exception e) {
            log.debug("Translation handling exception", e);
        }
        return body;
    }

    private void processTranslation(Object obj) {
        if (obj == null) {
            return;
        }

        if (obj instanceof Collection<?> collection) {
            for (Object item : collection) {
                processTranslation(item);
            }
            return;
        }

        // Handle common wrapper types (such as PageResult, Result, etc.)
        processWrapperFields(obj);

        if (!(obj instanceof TransPojo)) {
            return;
        }

        transService.trans(obj);
    }

    /**
     * Handle fields that may contain TransPojo in wrapper classes
     */
    private void processWrapperFields(Object obj) {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null) {
                    continue;
                }

                if (value instanceof Collection<?> collection) {
                    for (Object item : collection) {
                        if (item instanceof TransPojo) {
                            transService.trans(item);
                        }
                    }
                } else if (value instanceof TransPojo) {
                    transService.trans(value);
                } else if (!value.getClass().getName().startsWith("java.") && !value.getClass().getName().startsWith("javax.")) {
                    processWrapperFields(value);
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
