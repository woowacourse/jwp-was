package webserver.http.request;

import java.util.Map;

class HttpRequestParameters {
    private final Map<String, String> requestParameters;

    public HttpRequestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public String get(String key) {
        return requestParameters.get(key);
    }
}
