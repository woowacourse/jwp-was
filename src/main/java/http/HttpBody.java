package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import utils.IOUtils;

public class HttpBody {
    private static final String DATA_DIVIDER = "&";
    private static final String KEY_VALUE_DIVIDER = "=";

    private final Map<String, String> httpBody = new LinkedHashMap<>();

    public HttpBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        String content = IOUtils.readData(bufferedReader, contentLength);
        String[] splitBodies = content.split(DATA_DIVIDER);
        for (String splitBody : splitBodies) {
            String[] bodyKeyAndValue = splitBody.split(KEY_VALUE_DIVIDER);
            this.httpBody.put(bodyKeyAndValue[0], bodyKeyAndValue[1]);
        }
    }

    public String getValue(String key) {
        return httpBody.get(key);
    }
}
