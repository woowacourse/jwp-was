package http.request;

import http.common.HttpVersion;
import http.request.exception.InvalidRequestLineException;

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
        String[] tokens = line.split(BLANK);
        validRequestLine(tokens);

        return new RequestLine(HttpMethod.valueOf(tokens[0]), new Url(tokens[1]), HttpVersion.of(tokens[2]));
    }

    private static void validRequestLine(final String[] tokens) {
        if (tokens.length == 3) {
            return;
        }

        throw new InvalidRequestLineException();
    }

    public String splitQueryString() {
        return extractQueryString(getOriginUrl().split(QUERY_STRING_DELIMITER));
    }

    private String extractQueryString(final String[] tokens) {
        if (tokens.length == 2) {
            return tokens[1];
        }

        return EMPTY;
    }

    public String getOriginUrl() {
        return url.getOriginUrl();
    }

    public String getFullUrl() {
        return url.getFullUrl();
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
