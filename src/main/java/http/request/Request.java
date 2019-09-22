package http.request;

public class Request {
    private RequestMethod requestMethod;
    private RequestUrl url;
    private RequestInformation requestInformation;

    public Request(RequestMethod requestMethod, RequestUrl url, RequestInformation requestInformation) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.requestInformation = requestInformation;
    }

    public String createKey() {
        return requestMethod.name() + " " + requestInformation.getOriginUrlPath();
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public RequestUrl getUrl() {
        return url;
    }

    public RequestInformation getRequestInformation() {
        return requestInformation;
    }
}
