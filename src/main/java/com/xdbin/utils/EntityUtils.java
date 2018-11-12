package com.xdbin.utils;

import com.xdbin.annotation.Args;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Author: BaoXuebin
 * Date: 2018/10/29
 * Time: 下午9:15
 */
public class EntityUtils {

    public static <T> T cast(Object[] result, Class<T> clazz) {
        List<Class> list = getClassList(clazz);
        try {
            Constructor<T> constructor = clazz.getConstructor(list.toArray(new Class[list.size()]));
            return constructor.newInstance(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> cast(List<Object[]> results, Class<T> clazz) {
        List<Class> list = getClassList(clazz);
        return results.stream().map(result -> cast(list, result, clazz)).collect(toList());
    }

    public static <T> T cast(List<Class> list, Object[] result, Class<T> clazz) {
//        Arrays.asList(result).forEach(o -> System.out.println(o));
//        try {
//            Constructor<T> constructor = clazz.getConstructor(list.toArray(new Class[list.size()]));
//            for (int i = 0; i < list.size(); i++) {
//                Object value = result[i];
//                Class<T> c = list.get(i);
//                if (Integer.class.equals(c)) {
//                    result[i] = Integer.parseInt(value);
//                }
//            }
//            return constructor.newInstance(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return null;
    }

    private static <T> List<Class> getClassList(Class<T> clazz) {
        List<Class> list = new ArrayList<>();
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            Args args = field.getAnnotation(Args.class);
            if (args != null) {
                list.add(args.order(), field.getType());
            }
        });
        return list;
    }

}
