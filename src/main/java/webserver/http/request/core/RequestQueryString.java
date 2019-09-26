package webserver.http.request.core;

public class RequestQueryString extends RequestData {
    private final RequestPath requestPath;

    public RequestQueryString(RequestPath requestPath) {
        super();
        this.requestPath = requestPath;
        parse();
    }

    private void parse() {
        String[] params = requestPath.getFullPath().split("\\?");
        extractParameter(params[1].split("&"));
    }
}
