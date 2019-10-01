package dev.luffy.http.request;

import java.util.Map;

import dev.luffy.http.excption.NotExistKeyException;

public class HttpRequestData {

    private static final String NOT_EXIST_KEY_MESSAGE = "존재하지 않는 key 입니다.";

    private final Map<String, String> data;

    HttpRequestData(Map<String, String> data) {
        this.data = data;
    }

    public String get(String key) {
        if (!data.containsKey(key)) {
            throw new NotExistKeyException(NOT_EXIST_KEY_MESSAGE);
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
