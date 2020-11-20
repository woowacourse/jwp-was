package domain.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class RequestParams {

    private static final String QUERY_DELIMITER = "[&;]";
    private static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> params = new HashMap<>();

    public RequestParams(String query) throws UnsupportedEncodingException {
        String[] params = query.split(QUERY_DELIMITER);
        for (String param : params) {
            String[] keyAndValue = param.split(KEY_VALUE_DELIMITER);
            String value = "";
            if (hasNonEmptyValue(keyAndValue)) {
                value = URLDecoder.decode(keyAndValue[1], StandardCharsets.UTF_8.toString());
            }
            this.params.put(keyAndValue[0], value);
        }
    }

    private boolean hasNonEmptyValue(String[] keyAndValue) {
        return keyAndValue.length == 2;
    }

    public String get(String key) {
        return params.get(key);
    }
}
