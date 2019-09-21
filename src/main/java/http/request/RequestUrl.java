package http.request;

public class RequestUrl {
    private RequestUrlType requestUrlType;
    private String urlPath;

    private RequestUrl(String urlPath) {
        this.requestUrlType = RequestUrlType.findType(urlPath);
        this.urlPath = requestUrlType.getFolderPath() + urlPath;
    }

    public static RequestUrl from(String url) {
        return new RequestUrl(url);
    }
}
