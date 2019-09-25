package dev.luffy.http;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestMapper {

    private static final Map<String, Method> map;

    static {
        map = new HashMap<>();
    }

    public static void add(String path, Method method) {
        map.put(path, method);
    }

    public static Method get(String path) {
        return map.get(path);
    }
}
