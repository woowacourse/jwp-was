package webserver.httpRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private final Map<String, String> parameters;

    private HttpRequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static HttpRequestBody of(String body) {
        Map<String, String> params = new HashMap<>();
        parseBody(body, params);

        return new HttpRequestBody(params);
    }

    private static void parseBody(String body, Map<String, String> params) {
        String[] split = body.split("&");
        for (String pair : split) {
            String[] split1 = pair.split("=");
            String key = split1[0];
            String value = "";
            if (split1.length == 2) {
                value = split1[1];
            }
            params.put(key, value);
        }
    }

    public static HttpRequestBody empty() {
        Map<String, String> parameters = new HashMap<>();
        return new HttpRequestBody(parameters);
    }

    public String getBodyParam(String key) {
        String value = parameters.get(key);
        if (value == null) {
            return "";
        }
        return value;
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(parameters);
    }
}
