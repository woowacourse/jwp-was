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

    public static RequestBody of(BufferedReader br, String value) throws IOException {
        String data = IOUtils.readData(br, Integer.parseInt(value));
        return new RequestBody(parseData(data));
    }

    private static Map<String, String> parseData(String data) {
        Map<String, String> queryParams = new HashMap<>();
        int index = data.indexOf(QUESTION_MARK);
        String[] params = data.substring(index+1).split(AMPERSAND);
        for (String param : params) {
            String[] entry = param.split(EQUAL_SIGN);
            queryParams.put(entry[0], entry[1]);
        }
        return queryParams;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
