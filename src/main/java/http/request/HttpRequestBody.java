package http.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {

    private static final String EMPTY = "";
    private static final String AND = "&";
    private static final String EQUAL = "=";

    private final Map<String, String> body;

    public HttpRequestBody(String paramLine) {
        this.body = handler(paramLine);
    }

    public static HttpRequestBody emptyBody() {
        return new HttpRequestBody(EMPTY);
    }

    private Map<String, String> handler(String paramLine) {
        if (paramLine.isEmpty()) {
            return new HashMap<>();
        }
        return mapping(paramLine);
    }

    private Map<String, String> mapping(final String paramLine) {
        Map<String, String> body = new HashMap<>();
        String[] params = paramLine.split(AND);

        for (String param : params) {
            String[] data = param.split(EQUAL);
            body.put(data[0], data[1]);
        }

        return body;
    }

    public String getValue(final String name) {
        return this.body.get(name);
    }

    public Map<String, String> getBody() {
        return this.body;
    }
}
