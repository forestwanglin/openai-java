package xyz.felh.openai.utils;

import java.util.List;
import java.util.Map;

/**
 * @author Forest Wang
 * @package cn.magicwindow.common.util
 * @class Preconditions
 * @email forest@magicwindow.cn
 * @date 22/08/2017 17:26
 * @description
 */
public class Preconditions {

    /**
     * check if object is null for List Map String
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isBlank(T t) {

        boolean result = false;

        if (t == null) {
            return true;
        }

        if (t instanceof List) {
            if (((List) t).size() == 0) {
                return true;
            }
        } else if (t instanceof Map) {
            if (((Map) t).size() == 0) {
                return true;
            }
        } else if (t instanceof Object[]) {
            if (((Object[]) t).length == 0) {
                return true;
            }
        } else if (t instanceof String) {
            int strLen;

            strLen = ((String) t).length();
            if (strLen == 0) {
                return true;
            }

            for (int i = 0; i < strLen; i++) {
                if ((Character.isWhitespace(((String) t).charAt(i)) == false)) {
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
