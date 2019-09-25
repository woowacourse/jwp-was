package http.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {
    private static final String HEADER_DELIMITER = ": ";
    private static final String CRLF = "\r\n";

    private Map<String, String> requestHeader;

    private RequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    public static RequestHeader of(List<String> header) {
        RequestHeader requestHeader = new RequestHeader(new HashMap<>());
        for (String headerLine : header) {
            requestHeader.put(headerLine);
        }
        return requestHeader;
    }

    private void put(String headerLine) {
        String[] tokens = headerLine.split(HEADER_DELIMITER);
        if (tokens.length == 2) {
            requestHeader.put(tokens[0], tokens[1]);
        }
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : requestHeader.keySet()) {
            sb.append(key).append(HEADER_DELIMITER).append(requestHeader.get(key)).append(CRLF);
        }
        return sb.toString();
    }
}
