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
        body = URLDecoder.decode(body, "UTF-8");

        String[] attributes = body.split("&");
        for (String attribute : attributes) {
            addAttribute(parameters, attribute);
        }
        return new RequestBody(parameters);
    }

    private static void addAttribute(Map<String, String> parameters, String attribute) {
        String[] splitAttribute = attribute.split("=");
        String key = splitAttribute[0];
        String value = determineValue(splitAttribute);
        parameters.put(key, value);
    }

    private static String determineValue(String[] splitAttribute) {
        String value = "";
        if (splitAttribute.length == 2) {
            value = splitAttribute[1];
        }
        return value;
    }

    public String get(String key) {
        return parameters.get(key);
    }
}
