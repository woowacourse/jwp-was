package utils;

import java.util.HashMap;
import java.util.Map;

import web.HttpMethod;

public class HttpRequestUtils {

    public static HttpMethod extractHttpMethod(String line) {
        return HttpMethod.valueOf(line.split(" ")[0]);
    }

    public static String extractPath(String line) {
        return line.split(" ")[1];
    }

    public static Map<String, String> parseQueryString(String url) {
        Map<String, String> queryParams = new HashMap<>();
        int index = url.indexOf("?");
        String[] params = url.substring(index+1).split("&");
        for (String param : params) {
            String[] entry = param.split("=");
            queryParams.put(entry[0], entry[1]);
        }
        return queryParams;
    }

    public static String extractVersion(String line) {
        return line.split(" ")[2];
    }

    public static String extractHeaderKey(String line) {
        return line.split(": ")[0];
    }

    public static String extractHeaderValue(String line) {
        return line.split(": ")[1];
    }
}
