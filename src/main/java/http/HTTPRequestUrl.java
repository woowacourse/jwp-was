package http;

public class HTTPRequestUrl {
    private static final String TEMPLATES_FILE_PATH = "./templates";
    public static final String DELIMITER = " ";
    private final String url;

    public HTTPRequestUrl(String url) {
        this.url = url;
    }

    public static HTTPRequestUrl extractRequestUrl(String request) {
        System.out.println(request);
        return new HTTPRequestUrl(request.split(DELIMITER)[1]);
    }

    public String extractClassPath() {
        return TEMPLATES_FILE_PATH + this.url;
    }

    public String getUrl() {
        return url;
    }
}
