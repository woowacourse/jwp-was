package http.request;

import http.exception.InvalidRequestUrlException;

import java.util.Objects;

public class RequestUrl {
    private static final String BLANK = "";
    private static final String START_POINT_OF_QUERY_PARAMETER = "?";
    private static final String REGEX_START_POINT_OF_QUERY_PARAMETER = "\\?";
    private static final String ROOT_URL = "/";
    private static final String INDEX_HTML = "/index.html";
    private static final int INDEX_OF_URL = 0;
    private static final int INDEX_OF_QUERY_PARAMETER = 1;

    private final String url;
    private final String queryParameter;

    public RequestUrl(String requestUrl) {
        Objects.requireNonNull(requestUrl, "null point exception");
        this.url = initializeUrl(requestUrl);
        this.queryParameter = initializeQueryParameter(requestUrl);
    }

    private String initializeUrl(String requestUrl) {
        if (requestUrl.contains(START_POINT_OF_QUERY_PARAMETER)) {
            return validateRequestUrl(requestUrl, INDEX_OF_URL);
        }
        return requestUrl;
    }

    private String initializeQueryParameter(String requestUrl) {
        if (requestUrl.contains(START_POINT_OF_QUERY_PARAMETER)) {
            return validateRequestUrl(requestUrl, INDEX_OF_QUERY_PARAMETER);
        }
        return BLANK;
    }

    private String validateRequestUrl(String requestUrl, int urlOrQuery) {
        String[] splitedReqeustUrl = requestUrl.split(REGEX_START_POINT_OF_QUERY_PARAMETER, 2);

        if (splitedReqeustUrl.length != 2) {
            throw new InvalidRequestUrlException();
        }

        return splitedReqeustUrl[urlOrQuery];
    }

    public String getUrl() {
        return ROOT_URL.equals(url) ? INDEX_HTML : url;
    }

    public String getQueryParameter() {
        return queryParameter;
    }

    public boolean hasQueryParameter() {
        return !BLANK.equals(queryParameter);
    }
}
