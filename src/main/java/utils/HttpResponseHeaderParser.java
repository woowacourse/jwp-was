package utils;

public class HttpResponseHeaderParser {
    public static String response200Header(String contentType, int lengthOfBodyContent) {
        return "HTTP/1.1 200 OK \r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + lengthOfBodyContent + "\r\n" +
                "\r\n";
    }

    public static String response302Header(String location) {
        return "HTTP/1.1 302 Found \r\n" +
                "Location: " + location + "\r\n" +
                "\r\n";
    }

    public static String response404Header() {
        return "HTTP/1.1 404 Not Found \r\n" +
                "\r\n";
    }

    public static String response405Header() {
        return "HTTP/1.1 405 Method Not Allowed \r\n" +
                "\r\n";
    }

    public static String response400Header() {
        return "HTTP/1.1 400 Bad Request \r\n" +
                "\r\n";
    }

    public static String response500Header() {
        return "HTTP/1.1 500 Internal Server Error \r\n" +
                "\r\n";
    }
}
