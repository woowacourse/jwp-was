package webserver.support;

public class PathHandler {
    private static final String TEMPLATE_URL_FORMAT = "./templates%s";
    private static final String STATIC_URL_FORMAT = "./static%s";

    public static String path(String url) {
        if (url.contains(".html") || url.contains(".ico")) {
            return String.format(TEMPLATE_URL_FORMAT, url);
        }

        if (url.contains("create")) {
            return String.format(TEMPLATE_URL_FORMAT, "/index.html");
        }

        return String.format(STATIC_URL_FORMAT, url);
    }
}
