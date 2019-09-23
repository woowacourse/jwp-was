package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.MimeType;
import http.request.HttpRequestUrl;

public class HttpRequestUtils {

    public static Map<String, String> parse(List<String> lines) {
        return buildHeaders(lines, new HashMap<>());
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

    private static Map<String, String> buildParams(String dataSequence, Map<String, String> params) {
        String[] tokens = dataSequence.split("&");
        for (String token : tokens) {
            String[] paramKeyValue = token.split("=");
            params.put(paramKeyValue[0], paramKeyValue[1]);
        }
        return params;
    }

    public static Map<String, String> parse(String bodyData) {
        Map<String, String> body = new HashMap<>();
        if ("".equals(bodyData)) {
            return body;
        }
        return buildParams(bodyData, body);
    }

    public static String filePathBuilder(String path, MimeType mimeType) {
        if (!mimeType.isHtml()) {
            return "./static" + path;
        }
        return "./templates" + path;
    }
}
