package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHeader {
    private final MethodType method;
    private final String path;
    private final Map<String, String> queryParams;
    private final String protocolVersion;
    private final Map<String, String> attribute;

    private enum MethodType {
        GET, HEAD, POST, PUT, PATCH, DELETE, CONNECT, OPTIONS, TRACE;

        public static MethodType of(String input) {
            return Arrays.stream(MethodType.values())
                .filter(value -> value.name().equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> new UnsupportedMethodTypeException(input));
        }
    }

    public RequestHeader(String path, Map<String, String> queryParams, MethodType method, String protocolVersion,
        Map<String, String> attribute) {
        this.path = path;
        this.queryParams = queryParams;
        this.method = method;
        this.protocolVersion = protocolVersion;
        this.attribute = attribute;
    }

    public static RequestHeader of(String header) {
        try {
            String[] headers = header.split("\n");
            String[] startLine = headers[0].split(" ");
            MethodType method = MethodType.of(startLine[0]);
            String path = getPathWithoutParams(startLine[1]);
            Map<String, String> queryParams = getParams(startLine[1]);
            String protocolVersion = startLine[2];

            Map<String, String> attribute = new LinkedHashMap<>();
            for (int i = 1; i < headers.length; i++) {
                String[] map = headers[i].split(":");
                attribute.put(map[0], map[1].trim());
            }

            return new RequestHeader(path, queryParams, method, protocolVersion, attribute);
        } catch (Exception e) {
            throw new InvalidRequestHeaderException();
        }
    }

    private static Map<String, String> getParams(String path) {
        Map<String, String> queryParams = new LinkedHashMap<>();
        if (!path.contains("?")) {
            return queryParams;
        }
        String queryString = path.split("\\?")[1];
        String[] params = queryString.split("&");
        for (String param : params) {
            String[] attribute = param.split("=");
            queryParams.put(attribute[0], attribute[1]);
        }
        return queryParams;
    }

    private static String getPathWithoutParams(String path) {
        return path.split("\\?")[0];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(method)
            .append(" ")
            .append(path);

        if (!queryParams.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = queryParams.entrySet().iterator();
            builder.append("?");
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                builder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
                if (iterator.hasNext()) {
                    builder.append("&");
                }
            }
        }
        builder.append(" ")
            .append(protocolVersion)
            .append(System.lineSeparator());

        for (Map.Entry<String, String> entry : attribute.entrySet()) {
            builder.append(entry.getKey())
                .append(": ")
                .append(entry.getValue())
                .append(System.lineSeparator());
        }

        return builder.toString();
    }

    public String getPath() {
        return path;
    }

    public MethodType getMethod() {
        return method;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public Map<String, String> getAttribute() {
        return Collections.unmodifiableMap(attribute);
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public String getHeader(String header) {
        return attribute.getOrDefault(header, null);
    }
}
