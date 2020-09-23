package webserver.http.request;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import webserver.utils.ValueExtractor;

public class RequestParams {
    private final Map<String, List<String>> parameters;

    public RequestParams(Map<String, List<String>> parameters) {
        this.parameters = parameters;
    }

    public static RequestParams from(String params) {
        return new RequestParams(ValueExtractor.extract(params));
    }

    public List<String> getParameters(String key) {
        return parameters.get(key);
    }
}
