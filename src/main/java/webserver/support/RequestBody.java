package webserver.support;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> parameters = new HashMap<>();

    public RequestBody(String body) {
        if (!StringUtils.isBlank(body)) {
            String[] queryParams = body.split("&");
            for (String queryParam : queryParams) {
                String key = queryParam.split("=")[0];
                String value = queryParam.split("=")[1];
                parameters.put(key, value);
            }
        }
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(parameters);
    }
}
