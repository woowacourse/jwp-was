package utils;

import webserver.request.HttpRequest;

public class HttpSessionUtils {
    public static boolean isLogined(HttpRequest httpRequest) {
        return httpRequest.containHeaderField("Cookie", "logined=true");
    }
}
