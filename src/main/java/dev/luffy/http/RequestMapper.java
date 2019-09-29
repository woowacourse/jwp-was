package dev.luffy.http;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestMapper {

    private static final Map<RequestMapping, Method> map;

    static {
        map = new HashMap<>();
    }

    public static void add(RequestMapping request, Method method) {
        map.put(request, method);
    }

    public static Method get(RequestMapping request) {
        return map.get(request);
    }
}
