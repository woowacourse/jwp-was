package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private static final String DELIMITER_OF_FORM = "&";
    private static final String DELIMITER_OF_FORM_PARAMETER = "=";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> parameters = new HashMap<>();

    public RequestBody(String body) {
        if (!StringUtils.isBlank(body)) {
            String[] queryParams = body.split(DELIMITER_OF_FORM);
            splitRequestBody(queryParams);
        }
    }

    private void splitRequestBody(String[] queryParams) {
        for (String queryParam : queryParams) {
            String[] splitedQueryParam = queryParam.split(DELIMITER_OF_FORM_PARAMETER);
            String key = splitedQueryParam[KEY];
            String value = splitedQueryParam[VALUE];

            parameters.put(key, value);
        }
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(parameters);
    }
}
