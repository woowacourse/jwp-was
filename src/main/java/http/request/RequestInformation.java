package http.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestInformation {
    private static final String DELIMITER = " ";

    private Map<String, String> requestInformation;

    public RequestInformation(Map<String, String> requestInformation) {
        this.requestInformation = requestInformation;
    }

    public RequestMethod extractMethod() {
        return RequestMethod.from(tokenize()[0]);
    }

    public RequestUrl extractUrl() {
        return RequestUrl.from(tokenize()[1]);
    }

    private String[] tokenize() {
        String requestLine = requestInformation.get("Request-Line:");
        return requestLine.split(DELIMITER);
    }

    public String extractQueryParameters() {
        return requestInformation.get("Query-Parameters:");
    }

    public QueryParameters createQueryParametes() {

        Map<String, String> params = new HashMap<>();
        String queryParams = requestInformation.get("Query-Parameters:");
        String[] tokens = queryParams.split("&");
        Arrays.stream(tokens)
                .forEach(token -> {
                    String[] keyValues = token.split("=");
                    params.put(keyValues[0], keyValues[1]);
                });

        return new QueryParameters(params);
    }
}
