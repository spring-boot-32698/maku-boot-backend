package net.maku.framework.common.trans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Translation reflection utility class
 *
 * @author maku
 */
public class TransReflectUtils {

    /**
     * Get the list of fields with the specified annotation
     *
     * @param clazz           Class
     * @param annotationClass Annotation type
     * @return Field list
     */
    public static List<Field> getAnnotationField(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotationClass)) {
                    fields.add(field);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return fields;
    }

    /**
     * Get declared fields (including those of parent classes)
     *
     * @param clazz     Class
     * @param fieldName Field name
     * @return Field
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        return null;
    }
}
