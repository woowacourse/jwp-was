package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeader {
    private static final String HEADER_DELIMITER = ":\\s";
    private static final String CRLF = "\r\n";

    private Map<String, String> responseHeader;

    private ResponseHeader(Map<String, String> responseHeader) {
        this.responseHeader = responseHeader;
    }

    public static ResponseHeader of() {
        ResponseHeader responseHeader = new ResponseHeader(new HashMap<>());
        responseHeader.put("Content-Type", "text/plain;charset=utf-8");
        responseHeader.put("Content-Length", "0");

        return responseHeader;
    }

    public void put(String headerKey, String headerValue) {
        responseHeader.put(headerKey, headerValue);
    }

    public String getHeader(String key) {
        return responseHeader.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : responseHeader.keySet()) {
            sb.append(key).append(HEADER_DELIMITER).append(responseHeader.get(key)).append(CRLF);
        }
        return sb.toString();
    }
}
