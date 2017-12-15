package com.dp.common.utils.util;

import java.lang.reflect.Field;

public class BeanUtil {

    private BeanUtil() {}

    /**
     * 不涉及到父类
     */
    public static void setNullIntegerTo0(Object obj) throws IllegalAccessException {
        if (obj == null) {
            throw new IllegalArgumentException("对象不能为null");
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> type = field.getType();
            if (type == Integer.class) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null) {
                    field.set(obj, 0);
                }
            }
        }
    }
}
