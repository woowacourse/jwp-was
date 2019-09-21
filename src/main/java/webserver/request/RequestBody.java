package webserver.request;

import utils.DecoderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestBody {
    private static final String AMPERSAND = "&";
    private static final String EQUALS_SIGN = "=";

    private Map<String, String> body;

    public RequestBody() {
        this.body = new HashMap<>();
    }

    public String get(String key) {
        return Optional.ofNullable(body.get(key))
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("key 값(%s)에 해당되는 Body 데이터가 존재하지 않습니다.", key)));
    }

    public void put(String body) {
        String[] bodyParams = body.split(AMPERSAND);
        for (String param : bodyParams) {
            String[] queryParam = param.split(EQUALS_SIGN);
            this.body.put(DecoderUtils.decodeString(queryParam[0]), DecoderUtils.decodeString(queryParam[1]));
        }
    }
}
