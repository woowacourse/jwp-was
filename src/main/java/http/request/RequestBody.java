package http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private Map<String, String> body;

    public RequestBody(String paramLine) {
        this.body = handler(paramLine);
    }

    public static RequestBody emptyBody() {
        return new RequestBody("");
    }

    private Map<String, String> handler(String paramLine) {
        if (paramLine.isEmpty()) {
            return new HashMap<>();
        }
        return mapping(paramLine);
    }

    private Map<String, String> mapping(String paramLine) {
        Map<String, String> body = new HashMap<>();
        String[] params = paramLine.split("&");

        for (String param : params) {
            String[] data = param.split("=");
            body.put(data[0], data[1]);
        }

        return body;
    }

    public String getValue(String name) {
        return body.get(name);
    }

    public Map<String, String> getBody() {
        return body;
    }
}
