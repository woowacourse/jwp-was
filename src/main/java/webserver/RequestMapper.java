package webserver;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import http.request.MappedRequest;

public class RequestMapper {
    private static final Map<MappedRequest, Method> requestMapper = new LinkedHashMap<>();

    public static void add(MappedRequest mappedRequest, Method method) {
        requestMapper.put(mappedRequest, method);
    }

    public static Method get(MappedRequest mappedRequest) {
        return requestMapper.get(mappedRequest);
    }
}
