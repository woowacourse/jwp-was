package http.request;

import controller.ControllerMapper;

public class Request {
    private RequestMethod requestMethod;
    private RequestUrl url;
    private QueryParameters queryParameters;
    private RequestInformation requestInformation;

    public Request(RequestMethod requestMethod, RequestUrl url, RequestInformation requestInformation) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.queryParameters = requestInformation.createQueryParametes();
        this.requestInformation = requestInformation;
    }

    public RequestUrl getUrl() {
        return url;
    }

    public RequestInformation getRequestInformation() {
        return requestInformation;
    }

    public ControllerMapper createControllerMapper() {
        return new ControllerMapper(requestMethod, url.getOriginalUrlPath());
    }
}
