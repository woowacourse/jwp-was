package webserver.http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private static final String NEW_LINE = "\r\n";
    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : headers.keySet()) {
            sb.append(key);
            sb.append(headers.get(key));
            sb.append(NEW_LINE);
        }
        return sb.toString();
    }
}
