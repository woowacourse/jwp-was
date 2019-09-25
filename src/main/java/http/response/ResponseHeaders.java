package http.response;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ResponseHeaders {

    private Map<String, String> responseHeaders = new LinkedHashMap<>();


    public void addResponseHeaders(String key, String value) {
        responseHeaders.put(key, value);
    }

    public void append(StringBuilder sb) {
        if (!responseHeaders.isEmpty()) {
            for (String header : responseHeaders.keySet()) {
                sb.append(header).append(responseHeaders.get(header)).append("\r\n");
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseHeaders that = (ResponseHeaders) o;
        return Objects.equals(responseHeaders, that.responseHeaders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseHeaders);
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }
}
