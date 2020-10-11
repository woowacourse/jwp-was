package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int REQUEST_URI_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final String EXTENSION_DELIMITER = "\\.";
    private static final int NO_EXTENSION_SIZE = 1;
    private static final int SIZE_CORRECTION_NUMBER = 1;
    private static final String EXTENSION_LETTER = ".";
    private static final String PARAMETER_DELIMITER = "&";
    private static final String URI_PARAMETER_SEPARATOR = "\\?";
    private static final int PARAMETER_INDEX = 1;
    private static final String PARAMETER_KEY_VALUE_SEPARATOR = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private final Method method;
    private final String requestUri;
    private final String httpVersion;

    private RequestLine(String method, String requestUri, String httpVersion) {
        this.method = Method.of(method);
        this.requestUri = requestUri;
        this.httpVersion = httpVersion;
    }

    public static RequestLine of(String line) {
        String method = line.split(REQUEST_LINE_DELIMITER)[METHOD_INDEX];
        String requestUri = line.split(REQUEST_LINE_DELIMITER)[REQUEST_URI_INDEX];
        String httpVersion = line.split(REQUEST_LINE_DELIMITER)[HTTP_VERSION_INDEX];

        return new RequestLine(method, requestUri, httpVersion);
    }

    public boolean isSameMethod(Method method) {
        return this.method.equals(method);
    }

    public ContentType generateContentTypeFromRequestUri() {
        return ContentType.of(extractRequestUriExtension())
            .orElse(null);
    }

    private String extractRequestUriExtension() {
        String[] sections = requestUri.split(EXTENSION_DELIMITER);

        if (sections.length == NO_EXTENSION_SIZE) {
            return null;
        }
        return EXTENSION_LETTER + sections[sections.length - SIZE_CORRECTION_NUMBER];
    }

    public Map<String, String> extractParameters() {
        String[] separatedUri = requestUri.split(URI_PARAMETER_SEPARATOR);

        if (separatedUri.length == 1) {
            return null;
        }
        return makeParameters();
    }

    private Map<String, String> makeParameters() {
        Map<String, String> parameters = new HashMap<>();
        String parameterSection = requestUri.split(URI_PARAMETER_SEPARATOR)[PARAMETER_INDEX];
        String[] splitParameter = parameterSection.split(PARAMETER_DELIMITER);
        Arrays.stream(splitParameter)
            .forEach(p -> {
                String key = p.split(PARAMETER_KEY_VALUE_SEPARATOR)[KEY_INDEX];
                String value = p.split(PARAMETER_KEY_VALUE_SEPARATOR)[VALUE_INDEX];

                parameters.put(key, value);
            });

        return parameters;
    }

    public String getRequestUri() {
        return requestUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestLine that = (RequestLine) o;
        return method == that.method &&
            Objects.equals(requestUri, that.requestUri) &&
            Objects.equals(httpVersion, that.httpVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, requestUri, httpVersion);
    }
}
