package webserver.http.request.core;

import webserver.http.exception.CanNotParseDataException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestData {
    private static final String DATA_REGEX = "=";
    private final Map<String, String> requestBodyData;

    RequestData() {
        requestBodyData = new HashMap<>();
    }

    void extractParameter(String[] params) {
        Arrays.stream(params)
                .forEach(param -> {
                    String[] keyValues = param.split(DATA_REGEX);
                    if (keyValues.length != 2) {
                        throw new CanNotParseDataException();
                    }
                    requestBodyData.put(keyValues[0], keyValues[1]);
                });
    }

    public String getValue(String key) {
        return requestBodyData.get(key);
    }
}
