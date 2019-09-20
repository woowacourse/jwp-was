package http.request;

import webserver.RequestMethodType;

public class RequestHeader {

    private RequestMethodType requestMethodType;
    private String requestUri;
    private String requestBody;

    public RequestHeader(RequestMethodType requestMethodType, String requestUri) {
        this.requestMethodType = requestMethodType;
        this.requestUri = requestUri;
    }

    public RequestHeader(RequestMethodType requestMethodType, String requestUri, String requestBody) {
        this.requestMethodType = requestMethodType;
        this.requestUri = requestUri;
        this.requestBody = requestBody;
    }

    public RequestMethodType getRequestMethodType() {
        return requestMethodType;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "requestMethodType=" + requestMethodType +
                ", requestUri='" + requestUri + '\'' +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
