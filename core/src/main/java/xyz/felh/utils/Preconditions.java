package xyz.felh.utils;

import java.util.List;
import java.util.Map;

/**
 * @author Forest Wang
 * @package cn.magicwindow.common.util
 * @class Preconditions
 * @email forest@magicwindow.cn
 * @date 22/08/2017 17:26
 * @description Preconditions class
 */
public class Preconditions {

    /**
     * check if object is null for List Map String
     *
     * @param t   object
     * @param <T> Type T
     * @return isBlank
     */
    public static <T> boolean isBlank(T t) {
        boolean result = false;
        if (t == null) {
            return true;
        }
        if (t instanceof List) {
            if (((List<?>) t).isEmpty()) {
                return true;
            }
        } else if (t instanceof Map) {
            if (((Map<?, ?>) t).isEmpty()) {
                return true;
            }
        } else if (t instanceof Object[]) {
            if (((Object[]) t).length == 0) {
                return true;
            }
        } else if (t instanceof String) {
            int strLen = ((String) t).length();
            if (strLen == 0) {
                return true;
            }
            for (int i = 0; i < strLen; i++) {
                if (!Character.isWhitespace(((String) t).charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return result;
    }

    public static <T> boolean isNotBlank(T t) {
        return !isBlank(t);
    }

}
