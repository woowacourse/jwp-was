package http.request;

public class RequestUrl {
    private RequestContentType requestContentType;
    private String urlPath;

    private RequestUrl(String urlPath) {
        this.requestContentType = RequestContentType.findType(urlPath);
        this.urlPath = requestContentType.getDestinationPath() + urlPath;
    }

    public static RequestUrl from(String url) {
        return new RequestUrl(url);
    }

    public RequestContentType getRequestContentType() {
        return requestContentType;
    }

    public String getUrlPath() {
        return urlPath;
    }
}
