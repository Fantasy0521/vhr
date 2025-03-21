package org.javaboy.vhr.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于ThreadLocal封装工具类
 * 作用于同一个线程,浏览器每次发送一个请求就是一个独立的线程
 */
public class BaseContext {
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    static {
        threadLocal.set(new ConcurrentHashMap<>());
    }

    public static void put(String key, Object value){
        Map<String, Object> map = threadLocal.get();
        if (map == null){
            map = new ConcurrentHashMap<>();
        }
        map.put(key, value);
        threadLocal.set(map);
    }

    public static Object get(String key){
        Map<String, Object> map = threadLocal.get();
        return map.get(key);
    }
}
