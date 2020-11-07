package http;

public class HttpUrl {
    private static final String TEMPLATES_FILE_PATH = "./templates";
    public static final String DELIMITER = " ";
    private final String url;

    private HttpUrl(String url) {
        this.url = url;
    }

    public static HttpUrl extractRequestUrl(String request) {
        return new HttpUrl(request.split(DELIMITER)[1]);
    }

    public static HttpUrl from(String httpUrl) {
        return new HttpUrl(httpUrl);
    }

    public String extractClassPath() {
        return TEMPLATES_FILE_PATH + this.url;
    }

    public String getUrl() {
        return url;
    }
}
