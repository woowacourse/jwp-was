package http.request;

import http.common.URL;
import http.request.exception.InvalidRequestException;
import utils.StringUtils;

public class RequestLine {
    private static final String WHITE_SPACE = " ";
    private static final int REQUEST_LINE_SIZE = 3;
    private static final int REQUEST_METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;
    private final RequestMethod method;
    private final URL url;
    private final String version;

    public RequestLine(String requestLine) {
        String[] requestLineFields = splitRequestLine(requestLine);
        method = RequestMethod.of(requestLineFields[REQUEST_METHOD_INDEX]);
        url = URL.of(requestLineFields[URL_INDEX]);
        version = requestLineFields[HTTP_VERSION_INDEX];
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
