package web;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private static final String URL_DELIMITER = "&";

    private String body;

    public RequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        if (contentLength == 0) {
            return;
        }
        this.body = IOUtils.readData(bufferedReader, contentLength);
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> parse() {
        String[] splitBody = body.split(URL_DELIMITER);

        Map<String, String> bodies = new HashMap<>();
        for (String body : splitBody) {
            UrlParameter parameter = new UrlParameter(body);
            bodies.put(parameter.getKey(), parameter.getValue());
        }
        return bodies;
    }
}
