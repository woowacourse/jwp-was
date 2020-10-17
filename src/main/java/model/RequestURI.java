package model;

public class RequestURI {
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int URI = 0;
    private static final int QUERY_PARAMS = 1;

    private final String uri;
    private final HttpQueryParams httpQueryParams;

    private RequestURI(String uri, HttpQueryParams httpQueryParams) {
        this.uri = uri;
        this.httpQueryParams = httpQueryParams;
    }

    public static RequestURI of(String requestURI) {
        String[] uriAndQueryParams = requestURI.split(QUERY_STRING_DELIMITER);
        return new RequestURI(uriAndQueryParams[URI],
                HttpQueryParams.of(uriAndQueryParams[QUERY_PARAMS]));
    }

    public String getUri() {
        return uri;
    }

    public HttpQueryParams getHttpQueryParams() {
        return httpQueryParams;
    }
}
