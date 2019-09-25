package http.request;

import utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestParameter {
    public static final RequestParameter EMPTY = new RequestParameter("");
    private static final String REQUEST_PARAMETER_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int REQUEST_PARAMETER_KEY_INDEX = 0;
    private static final int REQUEST_PARAMETER_VALUE_INDEX = 1;
    private static final int REQUEST_PARAMETER_SIZE = 2;
    private final Map<String, String> requestParameters = new HashMap<>();

    public RequestParameter(String queryString) {
        if (StringUtils.isNotEmpty(queryString)) {
            parse(queryString);
        }
    }

    private void parse(String queryString) {
        for (String requestParameter : queryString.split(REQUEST_PARAMETER_DELIMITER)) {
            putRequestParameter(requestParameter.split(KEY_VALUE_DELIMITER));
        }
    }

    private void putRequestParameter(String[] requestParameters) {
        if (isValidRequestParameter(requestParameters)) {
            this.requestParameters.put(decodeUTF8(requestParameters[REQUEST_PARAMETER_KEY_INDEX]), decodeUTF8(requestParameters[REQUEST_PARAMETER_VALUE_INDEX]));
        }
    }

    private boolean isValidRequestParameter(String[] requestParameter) {
        return requestParameter.length == REQUEST_PARAMETER_SIZE && !StringUtils.isEmpty(requestParameter[REQUEST_PARAMETER_KEY_INDEX]);
    }

    private String decodeUTF8(String encodedString) {
        try {
            return URLDecoder.decode(encodedString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return encodedString;
        }
    }

    public String getParameter(String key) {
        return requestParameters.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestParameter that = (RequestParameter) o;
        return Objects.equals(requestParameters, that.requestParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestParameters);
    }
}
