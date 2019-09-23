package webserver.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> parameters;

    private RequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static RequestBody of(BufferedReader br, int contentLength) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        String body = IOUtils.readData(br, contentLength);
        URLDecoder.decode(body, "UTF-8");

        String[] attributes = body.split("&");
        for (String attribute : attributes) {
            String[] splitAttribute = attribute.split("=");
            String key = splitAttribute[0];
            String value = "";
            if (splitAttribute.length == 2) {
                value = splitAttribute[1];
            }
            parameters.put(key, value);
        }
        return new RequestBody(parameters);
    }

    public String get(String key) {
        return parameters.get(key);
    }
}
