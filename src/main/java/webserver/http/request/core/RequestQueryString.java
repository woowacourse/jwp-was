package webserver.http.request.core;

public class RequestQueryString extends RequestData {
    private static final String PATH_REGEX = "\\?";
    private static final String QUERY_STRING_REGEX = "&";
    private final RequestPath requestPath;

    public RequestQueryString(RequestPath requestPath) {
        super();
        this.requestPath = requestPath;
        parse();
    }

    private void parse() {
        String[] params = requestPath.getFullPath().split(PATH_REGEX);
        extractParameter(params[1].split(QUERY_STRING_REGEX));
    }
}
