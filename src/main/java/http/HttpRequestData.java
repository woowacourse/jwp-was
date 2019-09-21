package http;

import java.util.Map;

public class HttpRequestData {

    private final Map<String, String> data;

    public HttpRequestData(Map<String, String> data) {
        this.data = data;
    }

    public String get(String key) {
        if (!data.containsKey(key)) {
            throw new IllegalArgumentException("존재하지 않는 key 입니다.");
        }
        return data.get(key);
    }

    @Override
    public String toString() {
        return "HttpRequestData{" +
                "body=" + data +
                '}';
    }
}
