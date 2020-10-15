package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {

    private static final String COLON = ": ";

    private RequestLine requestLine;
    private Map<String, String> params;

    private RequestHeader(RequestLine requestLine, Map<String, String> params) {
        this.requestLine = requestLine;
        this.params = params;
    }

    public static RequestHeader from(BufferedReader br) throws IOException {
        RequestLine requestLine = RequestLine.from(br.readLine());
        Map<String, String> headerMap = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line)) {
            if (Objects.isNull(line)) {
                break;
            }
            String[] values = line.split(COLON);
            headerMap.put(values[0], values[1]);
            line = br.readLine();
        }
        return new RequestHeader(requestLine, headerMap);
    }

    public String getValue(String key) {
        return this.params.get(key);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getParams() {
        return params;
    }
}
