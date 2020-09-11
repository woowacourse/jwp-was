package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private Map<String, String> requestBodies = new HashMap<>();

    public RequestBody(BufferedReader br, Integer contentLength) throws IOException {
        String line = IOUtils.readData(br, contentLength);
        if (!"".equals(line)) {
            String[] tokens = line.split("&");
            this.requestBodies = parseRequestBody(tokens);
        }
    }

    private Map<String, String> parseRequestBody(String[] tokens) {
        Map<String, String> result = new HashMap<>();
        for (String token : tokens) {
            String[] value = token.split("=");
            result.put(value[0], value[1]);
        }
        return result;
    }

    public Map<String, String> getRequestBodies() {
        return requestBodies;
    }
}
