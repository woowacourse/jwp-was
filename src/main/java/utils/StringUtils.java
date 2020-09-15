package utils;

import web.HttpMethod;

public class StringUtils {

    public static HttpMethod extractHttpMethod(String line) {
        return HttpMethod.valueOf(line.split(" ")[0]);
    }

    public static String extractPath(String line) {
        return line.split(" ")[1];
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
