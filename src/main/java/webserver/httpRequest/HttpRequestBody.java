package webserver.httpRequest;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private final Map<String, String> parameters;

    private HttpRequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static HttpRequestBody of(BufferedReader br, int contentLength) {
        Map<String, String> params = new HashMap<>();
        String body = readBody(br, contentLength);

        String[] split = body.split("&");
        for (String pair : split) {
            String[] split1 = pair.split("=");
            String key = split1[0];
            String value = "";
            if (split1.length == 2) {
                value = split1[1];
            }
            params.put(key, value);
        }
        return new HttpRequestBody(params);
    }

    private static String readBody(BufferedReader br, int contentLength) {
        String body = null;
        try {
            body = IOUtils.readData(br, contentLength);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        try {
            URLDecoder.decode(body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException();
        }
        return body;
    }

    public static HttpRequestBody Empty() {
        Map<String, String> parameters = new HashMap<>();
        return new HttpRequestBody(parameters);
    }

    public String getBodyParam(String key) {
        String value = parameters.get(key);
        if (value == null) {
            return "";
        }
        return value;
    }

    public Map<String, String> getBody() {
        return Collections.unmodifiableMap(parameters);
    }
}
