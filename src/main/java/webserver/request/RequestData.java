package webserver.request;

import webserver.utils.DecoderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestData {
    private static final String AMPERSAND = "&";
    private static final String EQUALS_SIGN = "=";

    private Map<String, String> data;

    public RequestData() {
        this.data = new HashMap<>();
    }

    public String get(String key) {
        return Optional.ofNullable(data.get(key))
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("key 값(%s)에 해당되는 Body 데이터가 존재하지 않습니다.", key)));
    }

    public void put(String body) {
        String[] bodyParams = DecoderUtils.decodeString(body).split(AMPERSAND);
        for (String param : bodyParams) {
            String[] queryParam = param.split(EQUALS_SIGN);
            this.data.put(queryParam[0], queryParam[1]);
        }
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "data=" + data +
                '}';
    }
}

