package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private Map<String, String> params;

    public RequestBody(BufferedReader br, int contentLength) throws IOException {
        params = new HashMap<>();
        String line = IOUtils.readData(br, contentLength);
        if (line.isEmpty()) {
            throw new IllegalArgumentException();
        }

        String[] tokens = line.split("&");
        for (String token : tokens) {
            String[] keyValue = token.split("=");
            params.put(keyValue[0], keyValue[1]);
        }
    }

    public Map<String, String> getParams() {
        return params;
    }
}
