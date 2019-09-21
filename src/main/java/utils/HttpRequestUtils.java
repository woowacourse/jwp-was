package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.HttpRequestUrl;

public class HttpRequestUtils {

    public static Map<String, String> parse(List<String> lines) {
        Map<String, String> headers = new HashMap<>();
        for (String line : lines) {
            String[] splitLine = line.split(": ");
            headers.put(splitLine[0], splitLine[1]);
        }
        return headers;
    }

    public static Map<String, String> parse(HttpRequestUrl url) {
        Map<String, String> params = new HashMap<>();
        String[] queryParams = url.getParams().split("&");
        for (String queryParam : queryParams) {
            String[] paramKeyValue = queryParam.split("=");
            params.put(paramKeyValue[0], paramKeyValue[1]);
        }
        return params;
    }
}
