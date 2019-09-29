package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private static final String DELIMITER_OF_FORM = "&";
    private static final String DELIMITER_OF_FORM_PARAMETER = "=";
    private final Map<String, String> parameters = new HashMap<>();

    public HttpRequestBody(String body) {
        if (!StringUtils.isBlank(body)) {
            String[] queryParams = body.split(DELIMITER_OF_FORM);
            splitRequestBody(queryParams);
        }
    }

    private void splitRequestBody(String[] queryParams) {
        String[] keyAndValue;
        for (String queryParam : queryParams) {
            keyAndValue = queryParam.split(DELIMITER_OF_FORM_PARAMETER);
            String key = keyAndValue[0];
            String value = keyAndValue[1];

            parameters.put(key, value);
        }
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(parameters);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
