package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;

public class RequestBody {

    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL_SIGN = "=";

    private final Map<String, String> params;

    private RequestBody(Map<String, String> params) {
        this.params = params;
    }

    public static RequestBody of(BufferedReader br, String value, RequestLine requestLine) throws IOException {
        Map<String, String> queryParams = new HashMap<>();

        String data = IOUtils.readData(br, Integer.parseInt(value));
        parseFromUrl(queryParams, requestLine.getRawPath());
        parseFromBody(queryParams, data);

        return new RequestBody(queryParams);
    }

    private static void parseFromUrl(Map<String, String> queryParams, String path) {
        int index = path.indexOf(QUESTION_MARK);
        if (index == -1) {
            return;
        }
        String[] params = path.substring(index+1).split(AMPERSAND);
        addQueryParams(queryParams, params);
    }

    private static void parseFromBody(Map<String, String> queryParams, String data) {
        String[] params = data.split(AMPERSAND);
        addQueryParams(queryParams, params);
    }

    private static void addQueryParams(Map<String, String> queryParams, String[] params) {
        for (String param : params) {
            String[] entry = param.split(EQUAL_SIGN);
            queryParams.put(entry[0], entry[1]);
        }
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getParameter(String key) {
        return params.get(key);
    }
}
