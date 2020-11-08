package http;

public class HttpUrl {
    private static final String TEMPLATES_FILE_PATH = "./templates";
    private final String url;

    private HttpUrl(String url) {
        this.url = url;
    }

    public static HttpUrl from(String httpUrl) {
        return new HttpUrl(httpUrl);
    }

    public String extractFilePath() {
        return TEMPLATES_FILE_PATH + this.url;
    }

    public String getUrl() {
        return url;
    }
}
