package http.request;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private final Map<String, String> params;

    public RequestBody(BufferedReader br, int contentLength, String contentType) throws IOException {
        params = new HashMap<>();
        String body = IOUtils.readData(br, contentLength);
        if (body.isEmpty()) {
            return;
        }

        if (contentType.equals("application/x-www-form-urlencoded")) {
            parseWWWForm(body);
        } else {
            throw new UnsupportedOperationException("Unsupported content-type.");
        }
    }

    private void parseWWWForm(String body) throws UnsupportedEncodingException {
        String[] tokens = body.split("&");
        for (String token : tokens) {
            String[] keyValue = token.split("=");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("No value for the key: " + keyValue[0]);
            }
            params.put(keyValue[0].toLowerCase(), URLDecoder.decode(keyValue[1].trim(), "UTF-8"));
        }
    }

    public String getValue(String key) {
        return this.params.get(key);
    }
}
