package webserver;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private final Map<String, String> parameters;

    public HttpRequestBody(BufferedReader br, int contentLength) throws IOException {
        parameters = new HashMap<>();
        String body = IOUtils.readData(br, contentLength);
        URLDecoder.decode(body, "UTF-8");
        String[] split = body.split("&");
        for (String pair : split) {
            String[] split1 = pair.split("=");
            String key = split1[0];
            String value = "";
            if (split1.length == 2) {
                value = split1[1];
            }
            parameters.put(key, value);
        }
    }

    public String get(String key) {
        return parameters.get(key);
    }
}
