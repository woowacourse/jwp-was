package model.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import model.general.Method;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int METHOD_INDEX = 0;
    private static final int REQUEST_URI_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private static final String EXTENSION_DELIMITER = "\\.";
    private static final int NO_EXTENSION_SIZE = 1;
    private static final int SIZE_CORRECTION_NUMBER = 1;
    private static final String EXTENSION_LETTER = ".";
    private static final String PARAMETER_DELIMITER = ";";
    private static final String URI_PARAMETER_SEPARATOR = "\\?";
    private static final int URI_INDEX = 0;
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

    public String extractRequestUriExtension() {
        String[] sections = makePureUri(requestUri).split(EXTENSION_DELIMITER);

        if (sections.length == NO_EXTENSION_SIZE) {
            return null;
        }

        return EXTENSION_LETTER + sections[sections.length - SIZE_CORRECTION_NUMBER];
    }

    private String makePureUri(String uri) {
        return uri.split(URI_PARAMETER_SEPARATOR)[URI_INDEX];
    }

    public Map<String, String> extractUriParameters() {
        String[] separatedUri = requestUri.split(URI_PARAMETER_SEPARATOR);

        if (separatedUri.length == 1) {
            return Collections.emptyMap();
        }

        return makeParameters();
    }

    private Map<String, String> makeParameters() {
        String parameterSection = requestUri.split(URI_PARAMETER_SEPARATOR)[PARAMETER_INDEX];

        String unifiedParameterSection = parameterSection.replace("&", ";");
        Map<String, String> parameters = Arrays
            .stream(unifiedParameterSection.split(PARAMETER_DELIMITER))
            .map(it -> it.split(PARAMETER_KEY_VALUE_SEPARATOR))
            .collect(Collectors.toMap(it -> it[KEY_INDEX], it -> it[VALUE_INDEX]));

        return parameters;
    }

    public boolean isSameMethod(Method method) {
        return this.method.equals(method);
    }

    public boolean isSameUri(String inputUri) {
        String pueRequestUri = makePureUri(requestUri);
        String pureUri = makePureUri(inputUri);

        return pueRequestUri.equals(pureUri);
    }

    public boolean isStartsWithUri(String uri) {
        return requestUri.startsWith(uri);
    }

    public boolean whetherUriHasExtension() {
        return makePureUri(requestUri).contains(".");
    }

    public Method getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
}
