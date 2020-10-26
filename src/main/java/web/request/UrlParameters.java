package web.request;

import java.util.HashMap;
import java.util.Map;

public class UrlParameters {
    private static final String URL_DELIMITER = "&";

    private final Map<String, String> urlParameters = new HashMap<>();

    public static UrlParameters empty() {
        return new UrlParameters();
    }

    private UrlParameters() {
    }

    public UrlParameters(String body) {
        String[] splitBody = body.split(URL_DELIMITER);
        validate(splitBody);

        for (String param : splitBody) {
            UrlParameter parameter = new UrlParameter(param);
            urlParameters.put(parameter.getKey(), parameter.getValue());
        }
    }


    private void validate(String[] splitBody) {
        if (splitBody.length == 0) {
            throw new IllegalArgumentException("비어있습니다.");
        }
    }

    public String get(String key) {
        return urlParameters.get(key);
    }
}
