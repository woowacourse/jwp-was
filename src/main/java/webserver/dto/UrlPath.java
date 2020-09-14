package webserver.dto;

public class UrlPath {

    private final String urlPath;

    private UrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public static UrlPath from(String urlPath) {
        return new UrlPath(urlPath);
    }

    public String getUrlPath() {
        return urlPath;
    }
}
