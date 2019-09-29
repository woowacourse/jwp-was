package webserver.http.request;

public class Url {
    private String originUrl;
    private String fullUrl;

    public Url(final String originUrl) {
        this.originUrl = originUrl;
        this.fullUrl = HttpRequestType.redefineUrl(originUrl.split("\\?")[0]);
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    @Override
    public String toString() {
        return "Url{" +
                "originUrl='" + originUrl + '\'' +
                ", fullUrl='" + fullUrl + '\'' +
                '}';
    }
}
