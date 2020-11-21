package webserver.http.request;

public class RequestURIFactory {
    private static final String QUESTION_MARK = "?";
    private static final String QUERY_STRING_DELIMITER = "\\" + QUESTION_MARK;
    private static final int URI = 0;
    private static final int QUERY_PARAMS = 1;

    public RequestURI create(String uri) {
        String[] uriAndQueryParams = uri.split(QUERY_STRING_DELIMITER);
        return new RequestURI(uriAndQueryParams[URI],
                HttpParams.of(uriAndQueryParams[QUERY_PARAMS]));
    }
}
