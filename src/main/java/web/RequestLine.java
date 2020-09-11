package web;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestLine {
    public static final String URL_PARAMETER_DELIMITER = "\\?";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    public static final int METHOD_INDEX = 0;
    public static final int PATH_INDEX = 1;
    public static final int PROTOCOL_INDEX = 2;
    public static final int URL_INDEX = 0;
    public static final int PARAMETER_INDEX = 1;
    public static final int INDEX_ZERO = 0;
    public static final int INDEX_ONE = 1;
    private static final String REQUEST_LINE_DELIMITER = " ";
    private final String method;
    private final String path;
    private final String protocol;

    public RequestLine(String requestLine) {
        final String[] requests = requestLine.split(REQUEST_LINE_DELIMITER);
        this.method = requests[METHOD_INDEX];
        this.path = requests[PATH_INDEX];
        this.protocol = requests[PROTOCOL_INDEX];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String requestUrl() {
        return this.path.split(URL_PARAMETER_DELIMITER)[URL_INDEX];
    }

    public Map<String, String> parseParameters() {
        final String parameterString = this.path.split(URL_PARAMETER_DELIMITER)[PARAMETER_INDEX];
        final String[] pairs = parameterString.split(PARAMETER_DELIMITER);

        return Arrays.stream(pairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[INDEX_ZERO], it -> it[INDEX_ONE]));
    }
}
