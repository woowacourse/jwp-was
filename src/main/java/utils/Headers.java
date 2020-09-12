package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Headers {

    private Map<String, Object> headers;

    public Headers(List<String> lines) {
        headers = new HashMap<>();
        parseRequestFirstLine(lines.remove(0));
        parseHeaders(lines);
    }

    private void parseRequestFirstLine(String headerFirstLine) {
        String[] splitHeaderFirstLine = headerFirstLine.split(" ");

        headers.put("method", HttpMethod.of(splitHeaderFirstLine[0]));
        headers.put("filePath", splitHeaderFirstLine[1].split("\\?")[0]);
        headers.put("httpVersion", splitHeaderFirstLine[2]);
    }

    private void parseHeaders(List<String> requestHeaders) {
        requestHeaders.stream()
            .map(line -> line.split(": "))
            .forEach(pair -> headers.put(pair[0], pair[1]));
        parseAcceptType();
    }

    private void parseAcceptType() {

    }

    public boolean isGetRequest() {
        HttpMethod method = (HttpMethod) headers.get("method");
        return method.isGet();
    }

    public boolean isPostRequest() {
        HttpMethod method = (HttpMethod) headers.get("method");
        return method.isPost();
    }

    public Object getHeader(String name) {
        return headers.get(name);
    }
}
