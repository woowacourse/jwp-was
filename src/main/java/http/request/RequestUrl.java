package http.request;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUrl that = (RequestUrl) o;
        return requestContentType == that.requestContentType &&
                Objects.equals(originalUrlPath, that.originalUrlPath) &&
                Objects.equals(destinationFolderUrlPath, that.destinationFolderUrlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestContentType, originalUrlPath, destinationFolderUrlPath);
    }
}
