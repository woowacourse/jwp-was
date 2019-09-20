package http.request;

import http.common.URL;
import http.request.exception.InvalidRequestException;
import utils.StringUtils;

public class RequestLine {
    public static final int REQUEST_LINE_SIZE = 3;
    public static final String WHITE_SPACE = " ";
    private final RequestMethod method;
    private final URL url;
    private final String version;

    public RequestLine(String requestLine) {
        String[] tokens = splitRequestLine(requestLine);
        method = RequestMethod.of(tokens[0]);
        url = URL.of(tokens[1]);
        version = tokens[2];
    }

    private String[] splitRequestLine(String requestLine) {
        String[] tokens = StringUtils.split(requestLine, WHITE_SPACE);

        if (tokens == null || tokens.length != REQUEST_LINE_SIZE) {
            throw new InvalidRequestException();
        }

        return tokens;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public URL getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }
}
