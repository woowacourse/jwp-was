package webserver.http.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    PATCH,
    OPTIONS,
    TRACE;

    public static final Map<String, HttpMethod> mappings = new HashMap<>();

    static {
        Arrays.stream(values())
                .forEach(httpMethod -> mappings.put(httpMethod.name(), httpMethod));
    }

    public static HttpMethod of(final String httpMethod) {
        return mappings.get(httpMethod.toUpperCase());
    }
}
