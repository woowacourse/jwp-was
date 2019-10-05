package webserver.http;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Header {
    public static final String JSESSION_ID = "jsessionId";

    private Map<String, String> header;

    public Header(Map<String, String> header) {
        this.header = header;
    }

    public String get(String elementKey) {
        return header.get(elementKey);
    }

    public List<String> printHeader() {
        List<String> headers = new LinkedList<>();
        for (String elementKey : header.keySet()) {
            headers.add(String.format("%s : %s", elementKey, header.get(elementKey)));
        }
        return headers;
    }

    public void setSessionId(String sessionId) {
        header.put("Set-Cookie", String.format("%s=%s", JSESSION_ID, sessionId));
    }
}
