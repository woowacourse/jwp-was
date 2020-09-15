package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import utils.HttpRequestUtils;
import utils.IOUtils;

public class RequestBody {

    private final Map<String, String> params;

    private RequestBody(Map<String, String> params) {
        this.params = params;
    }

    public static RequestBody of(BufferedReader br, String value) throws IOException {
        String data = IOUtils.readData(br, Integer.parseInt(value));
        return new RequestBody(HttpRequestUtils.parseQueryString(data));
    }

    public Map<String, String> getParams() {
        return params;
    }
}
