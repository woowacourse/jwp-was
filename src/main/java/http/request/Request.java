package http.request;

public class Request {

    private RequestLine requestLine;
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public Request(RequestLine requestLine, RequestHeader requestHeader, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public String getUri() {
        return requestLine.getUri();
    }

    public String getHeader(String name) {
        return requestHeader.getValue(name);
    }

    public String getBody(String name) {
        return requestBody.getValue(name);
    }
}
