package webserver;

public class Response {
    public static final String INDEX_FILE = "/";
    public static final String INDEX_HTML = "/index.html";
    public static final String TEMPLATE_PREFIX = "./templates";
    public static final String STATIC_PREFIX = "./static";
    public static final String HTML_SUFFIX = ".html";
    public static final String ICO_SUFFIX = ".ico";

    private final String path;

    private Response(String path) {
        this.path = path;
    }

    public static Response of(Request request) {
        return new Response(request.getPath());
    }

    public static Response of(String path) {
        return new Response(path);
    }

    public String getPath() {
        if (INDEX_FILE.equals(path)) {
            return TEMPLATE_PREFIX + INDEX_HTML;
        }
        if (path.endsWith(HTML_SUFFIX) || path.endsWith(ICO_SUFFIX)) {
            return TEMPLATE_PREFIX + path;
        }
        return STATIC_PREFIX + path;
    }
}
