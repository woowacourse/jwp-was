package webserver.domain;

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

    private Map<String, String> params = new HashMap<>();

    public RequestParams(String query) throws UnsupportedEncodingException {
        String[] infos = query.split(QUERY_DELIMITER);
        for (String info : infos) {
            String[] keyAndValue = info.split(KEY_VALUE_DELIMITER);
            String value = "";
            if (hasNonEmptyValue(keyAndValue)) {
                value = URLDecoder.decode(keyAndValue[1], StandardCharsets.UTF_8.toString());
            }
            params.put(keyAndValue[0], value);
        }
    }

    private boolean hasNonEmptyValue(String[] keyAndValue) {
        return keyAndValue.length == 2;
    }
}
