package utils;

import http.request.Cookie;

public class HttpResponseHeaderParser {
    public static String ok(String contentType, int lengthOfBodyContent) {
        return "HTTP/1.1 200 OK \r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + lengthOfBodyContent + "\r\n" +
                "\r\n";
    }

    public static String found(String location) {
        return "HTTP/1.1 302 Found \r\n" +
                "Location: " + location + "\r\n" +
                "\r\n";
    }

    public static String found(String location, Cookie cookie) {
        return "HTTP/1.1 302 Found \r\n" +
                "Location: " + location + "\r\n" +
                "Set-Cookie: " + cookie.toString() + "Path=/" + "\r\n" +
                "\r\n";
    }

    public static String badRequest() {
        return "HTTP/1.1 400 Bad Request \r\n" +
                "\r\n";
    }

    public static String unauthorized(String contentType, boolean logined) {
        return "HTTP/1.1 401 Unauthorized \r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Set-Cookie: logined=" + logined + "\r\n" +
                "\r\n";
    }

    public static String notFound() {
        return "HTTP/1.1 404 Not Found \r\n" +
                "\r\n";
    }

    public static String methodNotAllowed() {
        return "HTTP/1.1 405 Method Not Allowed \r\n" +
                "\r\n";
    }

    public static String internalServerError() {
        return "HTTP/1.1 500 Internal Server Error \r\n" +
                "\r\n";
    }
}
