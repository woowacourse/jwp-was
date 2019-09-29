package http.request;

import http.session.Cookie;

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
        if (requestInformation.get("Query-Parameters:") != null) {
            String queryParams = requestInformation.get("Query-Parameters:");
            String[] tokens = queryParams.split("&");
            Arrays.stream(tokens)
                    .forEach(token -> {
                        String[] keyValues = token.split("=");
                        params.put(keyValues[0], keyValues[1]);
                    });
        }
        return new QueryParameters(params);
    }

    public String getParameter(String key) {
        String value = requestInformation.get(key);
        return value;
    }

    public Cookie createCookie() {
        if (requestInformation.get("Cookie:") == null) {
            return new Cookie();
        }
        String cookie = requestInformation.get("Cookie:");
        Map<String, String> cookieInformation = new HashMap<>();
        parseCookie(cookie, cookieInformation);
        return new Cookie(cookieInformation);
    }

    private void parseCookie(String cookie, Map<String, String> cookieInformation) {
        String[] tokens = cookie.split("; ");
        Arrays.stream(tokens)
                .forEach(token -> {
                    String[] keyValues = token.split("=");
                    cookieInformation.put(keyValues[0], keyValues[1]);
                });
    }
}
