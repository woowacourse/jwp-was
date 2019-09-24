package http.response;

import http.HTTP;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private static final String NEW_LINE = "\r\n";
    private static final String HEADER_DELIMITER = ": ";

    private final Map<HTTP, String> contents = new HashMap<>();

    public void addContents(HTTP http, String value) {
        contents.put(http, value);

    }

    public void add(Map<HTTP, String> header) {
        contents.putAll(header);
    }

    public String getContents(HTTP http) {
        return contents.get(http);
    }

    public boolean check(HTTP key) {
        return contents.containsKey(key);
    }

    public String getResponse() {
        StringBuffer sb = new StringBuffer();
        for (HTTP key : HTTP.values()) {
            if (contents.containsKey(key)) {
                sb.append(key.getPhrase()).append(HEADER_DELIMITER).append(contents.get(key)).append(NEW_LINE);
            }
        }
        return sb.toString();
    }
}
