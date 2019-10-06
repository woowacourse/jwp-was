package http.model;

import http.controller.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {
    private static final String HEADER_SEPARATOR = ": ";

    private Map<String, String> headers;

    public HttpHeaders() {
        this.headers = new HashMap<>();
    }

    public HttpHeaders(HttpMethod method, List<String> headerLines) {
        headers = new HashMap<>();

        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            parseHeaders(getHeaderWithoutMessageBody(headerLines));
        } else if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
            parseHeaders(headerLines);
        } else {
            throw new NotFoundException();
        }

    }

    private void parseHeaders(List<String> headerLines) {
        for (String headerLine : headerLines) {
            String[] header = headerLine.split(HEADER_SEPARATOR);
            headers.put(header[0], header[1]);
        }
    }

    private List<String> getHeaderWithoutMessageBody(List<String> headerLines) {
        if ("".equals(headerLines.get(headerLines.size() - 2))) {
            return headerLines.subList(0, headerLines.size() - 2);
        }
        return headerLines.subList(0, headerLines.size());
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
