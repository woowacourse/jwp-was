package http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpBody {
    private static final String DATA_DIVIDER = "&";
    private static final String KEY_VALUE_DIVIDER = "=";

    private Map<String, String> httpBody = new LinkedHashMap<>();

    public HttpBody(String httpBody) {
        String[] splitBodies = httpBody.split(DATA_DIVIDER);
        for (String splitBody : splitBodies) {
            String[] bodyKeyAndValue = splitBody.split(KEY_VALUE_DIVIDER);
            this.httpBody.put(bodyKeyAndValue[0], bodyKeyAndValue[1]);
        }
    }

    public String getValue(String key) {
        if (!httpBody.containsKey(key)) {
            throw new IllegalArgumentException("해당 key의 body 값이 없습니다.");
        }
        return httpBody.get(key);
    }
}
