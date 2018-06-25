package com.jh.jsuk.utils;

import com.jh.jsuk.exception.CannotConvertException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 枚举工具类
 */
@Slf4j
public class EnumUitl {

    /**
     * 通过value强转为 enum
     */
    public static <E extends Enum<E>, K> E toEnum(Class<E> clazz, K key) throws CannotConvertException {
        return toEnum(clazz, key, "getKey");
    }

    /**
     * 通过value强转为 enum
     */
    public static <E extends Enum<E>, K> E toEnum(Class<E> enumClass, K key, String methodName) throws CannotConvertException {
        E[] enums = enumClass.getEnumConstants();
        for (E enm : enums) {
            try {
                Method method = enm.getClass().getMethod(methodName);
                if (method.invoke(enm).equals(key))
                    return enm;
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                if (log.isWarnEnabled()) {
                    log.warn(e.getMessage());
                }
                throw new CannotConvertException();
            }
        }
        throw new CannotConvertException();
    }

    /**
     * 根据名称来转 枚举
     */
    public static <E extends Enum<E>> E valueOf(Class<E> clazz, String name) throws CannotConvertException {
        if (name == null || name.trim().equals(""))
            throw new CannotConvertException();
        try {
            return Enum.valueOf(clazz, name.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CannotConvertException();
        }

    }
}
