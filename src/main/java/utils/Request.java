package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

    private Map<String, Object> headers;

    public Request(List<String> requestHeaders) {
        headers = new HashMap<>();
        parseHttpMethods(requestHeaders.get(0));
    }

    private void parseHttpMethods(String headerFirstLine) {
        String[] splitHeaderFirstLine = headerFirstLine.split(" ");
        headers.put("method", HttpMethod.of(splitHeaderFirstLine[0]));
        headers.put("filePath", splitHeaderFirstLine[1]);
        headers.put("httpVersion", splitHeaderFirstLine[2]);
    }

    public Object getHeader(String name) {
        return headers.get(name);
    }
}
