package web;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {

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
        String[] splitBody = body.split("&");

        Map<String, String> bodies = new HashMap<>();
        for (String body : splitBody) {
            String[] keyValue = body.split("=");
            bodies.put(keyValue[0], keyValue[1]);
        }
        return bodies;
    }
}
