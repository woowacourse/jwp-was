package web;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestLine {
    public static final String URL_PARAMETER_DELIMITER = "\\?";
    public static final String PARAMETER_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    private static final String REQUEST_LINE_DELIMITER = " ";

    private final String method;
    private final Path path;
    private final Map<String, String> parameters;
    private final String protocol;

    public RequestLine(String requestLine) {
        final String[] requests = requestLine.split(REQUEST_LINE_DELIMITER);
        this.method = requests[0];
        this.path = new Path(parsePath(requests[1]));
        this.parameters = parseParameters(requests[1]);
        this.protocol = requests[2];
    }

    private String parsePath(String pathAndParams) {
        return pathAndParams.split(URL_PARAMETER_DELIMITER)[0];
    }

    private Map<String, String> parseParameters(String pathAndParams) {
        if (pathAndParams.split(URL_PARAMETER_DELIMITER).length == 1) {
            return null;
        }

        final String parameterString = pathAndParams.split(URL_PARAMETER_DELIMITER)[1];
        final String[] pairs = parameterString.split(PARAMETER_DELIMITER);

        return Arrays.stream(pairs)
                .map(parameter -> parameter.split(KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(it -> it[0], it -> it[1]));
    }

    public boolean isStaticFile() {
        return this.path.isStaticFile();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path.getPath();
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(path, that.path) &&
                Objects.equals(protocol, that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path, protocol);
    }
}
