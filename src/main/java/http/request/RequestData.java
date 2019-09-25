package http.request;

import http.exception.CanNotParseDataException;
import http.request.core.RequestPath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestData {
    private final Map<String, String> data = new HashMap<>();

    public RequestData(RequestPath url) {
        String[] params = url.getFullPath().split("\\?");
        if (params.length == 1) {
            throw new CanNotParseDataException();
        }
        extractParameter(params[1].split("&"));
    }

    public RequestData(String bodyData) {
        String[] params = bodyData.split("&");
        if (params.length == 0) {
            throw new CanNotParseDataException();
        }
        extractParameter(params);
    }

    private void extractParameter(String[] params) {
        Arrays.stream(params)
                .forEach(param -> {
                    String[] keyValues = param.split("=");
                    data.put(keyValues[0], keyValues[1]);
                });
    }

    public Map<String, String> getData() {
        return data;
    }
}
