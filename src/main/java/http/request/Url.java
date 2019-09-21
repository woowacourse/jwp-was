package http.request;

public class Url {
    private String originUrl;
    private String fullUrl;

    public Url(final String originUrl) {
        this.originUrl = originUrl;
        this.fullUrl = HttpRequestType.of(originUrl).getPrefix() + originUrl;
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
