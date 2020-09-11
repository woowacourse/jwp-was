package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private final String path;
    private final MethodType method;
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

    public RequestHeader(String path, MethodType method, String protocolVersion,
        Map<String, String> attribute) {
        this.path = path;
        this.method = method;
        this.protocolVersion = protocolVersion;
        this.attribute = attribute;
    }

    public static RequestHeader of(String header) {
        try {
            String[] headers = header.split("\n");
            String[] startLine = headers[0].split(" ");
            MethodType method = MethodType.of(startLine[0]);
            String path = startLine[1];
            String protocolVersion = startLine[2];

            Map<String,String> attribute = new HashMap<>();
            for (int i = 1; i < headers.length; i++) {
                String[] map = headers[i].split(":");
                attribute.put(map[0], map[1].trim());
            }

            return new RequestHeader(path, method, protocolVersion, attribute);
        } catch (Exception e) {
            throw new InvalidRequestHeaderException();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(method)
            .append(" ")
            .append(path)
            .append(" ")
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
}
