package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Request {

    private Map<String, Object> headers;
    private Map<String, String> params;

    public Request(List<String> requestHeaders) {
        headers = new HashMap<>();
        params = new HashMap<>();
        parseRequestFirstLine(requestHeaders.get(0));
    }

    private void parseRequestFirstLine(String headerFirstLine) {
        String[] splitHeaderFirstLine = headerFirstLine.split(" ");

        headers.put("method", HttpMethod.of(splitHeaderFirstLine[0]));
        headers.put("filePath", splitHeaderFirstLine[1].split("\\?")[0]);
        headers.put("httpVersion", splitHeaderFirstLine[2]);

        parseParams(splitHeaderFirstLine[1]);
    }

    private void parseParams(String filePath) {
        if (!filePath.contains("?")) {
            return;
        }
        String paramsSequence = filePath.split("\\?")[1];

        Stream.of(paramsSequence.split("&"))
            .map(param -> param.split("="))
            .forEach(pair -> params.put(pair[0], pair[1]));
    }

    public Object getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, String> getParams() {
        return params;
    }
}
