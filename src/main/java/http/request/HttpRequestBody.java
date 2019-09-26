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
        for (String queryParam : queryParams) {
            String key = queryParam.split(DELIMITER_OF_FORM_PARAMETER)[0];
            String value = queryParam.split(DELIMITER_OF_FORM_PARAMETER)[1];

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
