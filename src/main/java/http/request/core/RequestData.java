package http.request.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestData {
    private final Map<String, String> data = new HashMap<>();

    public RequestData(RequestPath path) {
        String[] params = path.getRequestPath().split("\\?");
        extractParameter(params[1].split("&"));
    }

    public RequestData(String bodyData) {
        String[] params = bodyData.split("&");
        extractParameter(params);
    }

    private Map<String, String> extractParameter(String[] params) {
        Arrays.stream(params)
                .forEach(param -> {
                    String[] keyValues = param.split("=");
                    data.put(keyValues[0], keyValues[1]);
                });

        return data;
    }

    public Map<String, String> getData() {
        return data;
    }
}
