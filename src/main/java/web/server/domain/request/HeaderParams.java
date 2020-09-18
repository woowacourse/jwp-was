package web.server.domain.request;

import java.util.HashMap;
import java.util.Map;

public class HeaderParams {
    private final Map<String, String> headerParams;

    public HeaderParams() {
        this.headerParams = new HashMap<>();
    }

    public void put(String key, String value) {
        headerParams.put(key.toLowerCase(), value);
    }

    public String get(String key) {
        return headerParams.get(key.toLowerCase());
    }

    public String getOrDefault(String key, String defaultValue) {
        return headerParams.getOrDefault(key.toLowerCase(), defaultValue);
    }
}
