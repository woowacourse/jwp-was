package http.request;

import http.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String FIRST_LINE_DELIMITER = " ";
    private static final String URL_DELIMITER = "\\?";

    private final RequestMethod requestMethod;
    private String path;
    private String query;
    private final String version;

    public RequestLine(String firstLine) {
        logger.debug("request-line contents: {}", firstLine);
        String[] tokens = firstLine.split(FIRST_LINE_DELIMITER);
        this.requestMethod = RequestMethod.valueOf(tokens[0]);
        urlParse(tokens[1]);
        this.version = tokens[2];
    }

    private void urlParse(String token) {
        String[] address = token.split(URL_DELIMITER);
        if (address.length > 1) {
            this.query = address[1];
        }
        this.path = address[0];
    }

    public RequestMethod getMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }


    public String getVersion() {
        return version;
    }

    public String getQuery() {
        return query;
    }
}
