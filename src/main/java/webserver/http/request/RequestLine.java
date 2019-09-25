package webserver.http.request;

import webserver.http.common.HttpVersion;
import webserver.http.request.exception.InvalidRequestLineException;

public class RequestLine {
    private static final String BLANK = " ";
    private static final String EMPTY = "";
    private static final String QUERY_STRING_DELIMITER = "\\?";

    private HttpMethod httpMethod;
    private Url url;
    private HttpVersion httpVersion;

    private RequestLine(final HttpMethod httpMethod, final Url url, final HttpVersion httpVersion) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.httpVersion = httpVersion;
    }

    public static RequestLine of(final String line) {
        String[] values = line.split(BLANK);
        validRequestLine(values);

        return new RequestLine(HttpMethod.valueOf(values[0]), new Url(values[1]), HttpVersion.of(values[2]));
    }

    private static void validRequestLine(final String[] values) {
        if (values.length == 3) {
            return;
        }

        throw new InvalidRequestLineException();
    }

    public String splitQueryString() {
        return extractQueryString(getOriginUrl().split(QUERY_STRING_DELIMITER));
    }

    private String extractQueryString(final String[] values) {
        if (values.length == 2) {
            return values[1];
        }

        return EMPTY;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getOriginUrl() {
        return url.getOriginUrl();
    }

    public String getFullUrl() {
        return url.getFullUrl();
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "httpMethod=" + httpMethod +
                ", url=" + url +
                ", httpVersion=" + httpVersion +
                '}';
    }
}
