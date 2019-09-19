package network;

public class Url {
    private String url;
    private RequestType requestType;

    public Url(final String url) {
        this.url = url;
        requestType = RequestType.value(url);
    }

    public String getFullUrl() {
        return requestType.getPrefix() + url;
    }

    @Override
    public String toString() {
        return "Url{" +
                "url='" + url + '\'' +
                ", dispatcher=" + requestType +
                '}';
    }
}
