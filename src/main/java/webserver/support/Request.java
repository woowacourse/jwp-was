package webserver.support;

public class Request {
    private static final int INDEX_OF_URL = 1;
    private static final String SPACE_DELIMITER = " ";

    private final String requestHeader;

    public Request(String header) {
        requestHeader = header;
    }

    public String extractUrl() {
        return requestHeader.split(SPACE_DELIMITER)[INDEX_OF_URL];
    }
}
