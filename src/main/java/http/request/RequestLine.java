package http.request;

import http.common.HttpVersion;
import http.common.URL;
import http.common.exception.NonExistentHttpVersionException;
import http.request.exception.InvalidRequestLineException;
import http.request.exception.NonExistentMethodException;
import utils.StringUtils;

public class RequestLine {
    private static final String REQUEST_LINE_SEPARATOR = " ";
    private static final int REQUEST_LINE_SIZE = 3;
    private static final int REQUEST_METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;

    private final RequestMethod method;
    private final URL url;
    private final HttpVersion version;
    private final RequestParameter queryParams;

    public RequestLine(String requestLine) {
        String[] requestLineFields = splitRequestLine(requestLine);
        try {
            this.method = RequestMethod.of(requestLineFields[REQUEST_METHOD_INDEX]);
            this.url = URL.of(requestLineFields[URL_INDEX]);
            this.version = HttpVersion.of(requestLineFields[HTTP_VERSION_INDEX]);
            this.queryParams = new RequestParameter(url.getQueryString());
        } catch (NonExistentMethodException | NonExistentHttpVersionException e) {
            throw new InvalidRequestLineException(e);
        }
    }

    private String[] splitRequestLine(String requestLine) {
        String[] requestLineFields = StringUtils.split(requestLine, REQUEST_LINE_SEPARATOR);

        if (isValidRequestLine(requestLineFields)) {
            throw new InvalidRequestLineException();
        }

        return requestLineFields;
    }

    private boolean isValidRequestLine(String[] requestLineFields) {
        return requestLineFields == null || requestLineFields.length != REQUEST_LINE_SIZE;
    }

    public String getQueryParameter(String key) {
        return queryParams.getParameter(key);
    }

    public RequestMethod getMethod() {
        return method;
    }

    public URL getUrl() {
        return url;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
