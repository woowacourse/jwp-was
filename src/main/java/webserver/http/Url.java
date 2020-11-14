package webserver.http;

public class Url {
    private final String url;

    private Url(String url) {
        this.url = url;
    }

    public static Url of(String url) {
        return new Url(url);
    }

    public boolean isEndsWith(String path) {
        return url.endsWith(path);
    }

    public String getUrl() {
        return url;
    }
}
