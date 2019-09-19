package network;

public class Url {
    private String url;
    private HttpRequestType httpRequestType;

    public Url(final String url) {
        this.url = url;
        httpRequestType = HttpRequestType.value(url);
    }

    public String getFullUrl() {
        return httpRequestType.getPrefix() + url;
    }

    @Override
    public String toString() {
        return "Url{" +
                "url='" + url + '\'' +
                ", dispatcher=" + httpRequestType +
                '}';
    }
}
