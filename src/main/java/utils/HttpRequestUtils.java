package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.HttpRequestUrl;

public class HttpRequestUtils {

    public static Map<String, String> parse(List<String> lines) {
        Map<String, String> headers = new HashMap<>();
        if (lines.contains("")) {
            return headers;
        }
        return buildHeaders(lines, headers);
    }

    private static Map<String, String> buildHeaders(List<String> lines, Map<String, String> headers) {
        for (String line : lines) {
            String[] splitLine = line.split(": ");
            headers.put(splitLine[0], splitLine[1]);
        }
        return headers;
    }

    public static Map<String, String> parse(HttpRequestUrl url) {
        Map<String, String> params = new HashMap<>();
        if (!url.hasParams() || url.isEmptyParams()) {
            return params;
        }
        return buildParams(url.getParams(), params);
    }

    private static Map<String, String> buildParams(String urlParams, Map<String, String> params) {
        String[] tokens = urlParams.split("&");
        for (String token : tokens) {
            String[] paramKeyValue = token.split("=");
            params.put(paramKeyValue[0], paramKeyValue[1]);
        }
        return params;
    }
}
