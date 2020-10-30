package web.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestHeader {

    private static final String COLON = ": ";

    private final Map<String, Object> params;

    private HttpRequestHeader(Map<String, Object> params) {
        this.params = params;
    }

    public static HttpRequestHeader from(BufferedReader br) throws IOException {
        Map<String, Object> headerMap = new HashMap<>();
        String line = br.readLine();
        while (!"".equals(line)) {
            if (Objects.isNull(line)) {
                break;
            }
            String[] values = line.split(COLON);
            headerMap.put(values[0], values[1]);
            line = br.readLine();
        }
        return new HttpRequestHeader(headerMap);
    }

    public Object getValue(String key) {
        return this.params.get(key);
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
