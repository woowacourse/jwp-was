package http.request;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RequestInformation {
    private static final String DELIMITER = " ";

    private Map<String, String> requestInformation;
    private List<String> requestUrlPatterns = Arrays.asList("user", "html", "css", "js", "ico", "woff", "ttf");

    public RequestInformation(Map<String, String> requestInformation) {
        this.requestInformation = requestInformation;
    }

    public RequestMethod extractMethod() {
        return RequestMethod.from(tokenize()[0]);
    }

    public RequestUrl extractUrl() {
        return RequestUrl.from(tokenize()[1]);
    }

    public String getOriginUrlPath() {
        return tokenize()[1];
    }

    private String[] tokenize() {
        String requestLine = requestInformation.get("Request-Line:");
        return requestLine.split(DELIMITER);
    }

    public String extractQueryParameters() {
        return requestInformation.get("Query-Parameters:");
    }
}
