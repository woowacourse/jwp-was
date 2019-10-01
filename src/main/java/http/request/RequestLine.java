package http.request;

import http.common.HttpMethod;
import http.common.HttpVersion;
import http.exception.InvalidHeaderException;
import org.springframework.util.StringUtils;

import java.util.*;

import static http.common.HttpMethod.GET;
import static http.common.HttpMethod.POST;

public class RequestLine {
    private static final String QUERY_STRING_DELIMITER = "?";
    private static final String QUERY_STRING_DELIMITER_REGEX = "\\?";
    private static final String REQUEST_LINE_DELIMITER_REGEX = " ";
    private static final String PARAMETERS_DELIMITER = "&";
    private static final String PARAMETER_KEY_VALUE_DELIMITER = "=";

    private final HttpMethod method;
    private String path;
    private Map<String, String> queryStringValues = new HashMap<>();
    private final HttpVersion version;

    public RequestLine(String requestLine) {
        List<String> tokens = makeTokensFrom(requestLine);
        method = HttpMethod.valueOf(tokens.get(0));
        path = tokens.get(1);
        String queryString = splitQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            parseData(queryString);
        }
        version = HttpVersion.getVersion(tokens.get(2));
    }

    private void parseData(String queryString) {
        List<String> tokens = Arrays.asList(queryString.split(PARAMETERS_DELIMITER));
        tokens.forEach(token -> {
            queryStringValues.put(token.split(PARAMETER_KEY_VALUE_DELIMITER)[0], token.split(PARAMETER_KEY_VALUE_DELIMITER)[1]);
        });
    }

    private String splitQueryString() {
        String pathWithQueryString = path;
        if (pathWithQueryString.contains(QUERY_STRING_DELIMITER)) {
            path = pathWithQueryString.split(QUERY_STRING_DELIMITER_REGEX)[0];
            return pathWithQueryString.split(QUERY_STRING_DELIMITER_REGEX)[1];
        }
        return "";
    }

    private static List<String> makeTokensFrom(String requestLine) {
        List<String> tokens = Arrays.asList(requestLine.split(REQUEST_LINE_DELIMITER_REGEX));
        validateRequestLine(requestLine, tokens);
        return tokens;
    }

    private static void validateRequestLine(String requestLine, List<String> tokens) {
        if (tokens.size() != 3 || !HttpMethod.matches(tokens.get(0)) || !HttpVersion.isExistVersion(tokens.get(2))) {
            throw new InvalidHeaderException(requestLine + "은 유효하지않은 HttpRequest입니다.");
        }
    }

    public boolean isGetMethod() {
        return GET.equals(method);
    }

    public boolean isPostMethod() {
        return POST.equals(method);
    }

    public boolean isExistValue(String fieldName) {
        return queryStringValues.containsKey(fieldName);
    }

    public String getPath() {
        return path;
    }

    public String getQueryString(String name) {
        if (!isExistValue(name)) {
            throw new InvalidHeaderException(name + "은 존재하지 않는 값입니다.");
        }
        return queryStringValues.get(name);
    }

    public HttpVersion getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return method == that.method &&
                Objects.equals(path, that.path) &&
                Objects.equals(queryStringValues, that.queryStringValues) &&
                version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, queryStringValues, version);
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method=" + method +
                ", path='" + path + '\'' +
                ", queryStringValues=" + queryStringValues +
                ", version=" + version +
                '}';
    }
}
