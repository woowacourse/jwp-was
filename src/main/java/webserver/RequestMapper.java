package webserver;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import http.request.MappedRequest;
import webserver.exception.DuplicatedMappedRequestException;

public class RequestMapper {
    private static final Map<MappedRequest, Method> requestMapper = new LinkedHashMap<>();

    public static void add(MappedRequest mappedRequest, Method method) {
        if (requestMapper.containsKey(mappedRequest)) {
            throw new DuplicatedMappedRequestException(
                String.format("중복되는 Mapped Request가 있습니다 : %s와 %s", method, requestMapper.get(mappedRequest)));
        }
        requestMapper.put(mappedRequest, method);
    }

    public static Method get(MappedRequest mappedRequest) {
        return requestMapper.get(mappedRequest);
    }

    public static boolean isContain(MappedRequest mappedRequest) {
        return null != get(mappedRequest);
    }
}
