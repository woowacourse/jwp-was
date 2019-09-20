package http.request;

import http.request.exception.InvalidQueryStringException;
import http.request.exception.ParameterNotFoundException;
import utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestParameter {
    private final Map<String, String> requestParameters = new HashMap<>();

    public RequestParameter(String queryString) {
        checkValidQueryString(queryString);
        parse(queryString);
    }

    private void checkValidQueryString(String queryString) {
        if (queryString == null) {
            throw new InvalidQueryStringException();
        }
    }

    private void parse(String queryString) {
        for (String requestParameter : queryString.split("&")) {
            putRequestParameter(requestParameter.split("="));
        }
    }

    private void putRequestParameter(String[] requestParameter) {
        if (requestParameter.length == 2 && !StringUtils.isEmpty(requestParameter[0])) {
            requestParameters.put(decodeUTF8(requestParameter[0]), decodeUTF8(requestParameter[1]));
        }
    }

    private String decodeUTF8(String encodedString) {
        try {
            return URLDecoder.decode(encodedString, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return encodedString;
        }
    }

    public String getParameter(String key) {
        return Optional.ofNullable(requestParameters.get(key))
                .orElseThrow(ParameterNotFoundException::new);
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
