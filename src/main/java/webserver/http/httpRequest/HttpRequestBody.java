package webserver.http.httpRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private static final String BODY_SEPARATOR = "&";
    private static final String BODY_PAIR_SEPARATOR = "=";
    private static final String DEFAULT_VALUE = "";

    private final Map<String, String> parameters;

    private HttpRequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static HttpRequestBody create(String body) {
        Map<String, String> params = new HashMap<>();
        parseBody(body, params);

        return new HttpRequestBody(params);
    }

    private static void parseBody(String body, Map<String, String> params) {
        String[] split = body.split(BODY_SEPARATOR);
        for (String pair : split) {
            parseEachQuery(params, pair);
        }
    }

    private static void parseEachQuery(Map<String, String> params, String pair) {
        String[] split1 = pair.split(BODY_PAIR_SEPARATOR);
        String key = split1[0];
        String value = DEFAULT_VALUE;
        if (hasValue(split1)) {
            value = split1[1];
        }
        params.put(key, value);
    }

    private static boolean hasValue(String[] split1) {
        return split1.length == 2;
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
