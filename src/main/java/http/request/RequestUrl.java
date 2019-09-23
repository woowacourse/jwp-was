package http.request;

public class RequestUrl {
    private RequestContentType requestContentType;
    private String originalUrlPath;
    private String destinationFolderUrlPath;

    private RequestUrl(String urlPath) {
        this.requestContentType = RequestContentType.findType(urlPath);
        this.originalUrlPath = makeOriginalUrlPath(urlPath);
        this.destinationFolderUrlPath = makeDestinationFolderUrlPath(urlPath);
    }

    private String makeOriginalUrlPath(String urlPath) {
        if ("/".equals(urlPath)) {
            return "/index.html";
        }
        return urlPath;
    }

    private String makeDestinationFolderUrlPath(String urlPath) {
        if ("/".equals(urlPath)) {
            return "../resources/templates/index.html";
        }
        return requestContentType.getDestinationPath() + urlPath;
    }

    public static RequestUrl from(String url) {
        return new RequestUrl(url);
    }

    public RequestContentType getRequestContentType() {
        return requestContentType;
    }

    public String getOriginalUrlPath() {
        return originalUrlPath;
    }

    public String getDestinationFolderUrlPath() {
        return destinationFolderUrlPath;
    }
}
