package http;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> params;

    public RequestBody(BufferedReader br, int contentLength) throws IOException {
        params = new HashMap<>();
        String body = IOUtils.readData(br, contentLength);
        if (body.isEmpty()) {
            return;
        }

        String[] tokens = body.split("&");
        for (String token : tokens) {
            String[] keyValue = token.split("=");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("No value for the key: " + keyValue[0]);
            }
            params.put(keyValue[0].toLowerCase(), keyValue[1]);
        }
    }

    public String getValue(String key) {
        return this.params.get(key);
    }
}
