package http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;

public class RequestBody {
    private static final String URL_DELIMITER = "&";
    private static final String BODY_DELIMITER = "=";

    private String body;

    public RequestBody(BufferedReader br, Integer contentLength) throws IOException {
        body = IOUtils.readData(br, contentLength);
    }

    public Map<String, String> parseRequestBody() {
        Map<String, String> result = new HashMap<>();

        if (!"".equals(body)) {
            String[] tokens = body.split(URL_DELIMITER);

            for (String token : tokens) {
                String[] value = token.split(BODY_DELIMITER);
                result.put(value[0], value[1]);
            }
        }

        return result;
    }

    public String getBody() {
        return body;
    }
}
