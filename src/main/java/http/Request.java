package http;

public class Request {
    private final RequestUri requestUri;
    private final RequestHeader requestHeader;

    public Request(RequestUri requestUri, RequestHeader requestHeader) {
        this.requestUri = requestUri;
        this.requestHeader = requestHeader;
    }

    public RequestUri getRequestUri() {
        return requestUri;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }
}
