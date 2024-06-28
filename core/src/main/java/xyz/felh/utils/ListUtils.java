package xyz.felh.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    /**
     * Cast object to List
     *
     * @param obj   object
     * @param clazz class
     * @param <T>   Type
     * @return the list
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

}
