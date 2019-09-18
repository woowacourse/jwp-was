package webserver.support;

public class Request {
    private static final int INDEX_OF_URL = 1;
    private static final String SPACE_DELIMITER = " ";
    private static final String ROOT_URL = "/";
    private static final String INDEX_HTML = "/index.html";

    private final String requestHeader;

    public Request(String header) {
        requestHeader = header;
    }

    public String extractUrl() {
        String[] url = requestHeader.split(SPACE_DELIMITER);
        return validate(url[INDEX_OF_URL]);
    }

    private String validate(String url) {
        if (url.equals(ROOT_URL)) {
            return INDEX_HTML;
        }
        return url;
    }
}
