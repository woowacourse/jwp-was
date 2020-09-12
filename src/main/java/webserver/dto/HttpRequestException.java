package webserver.dto;

public class HttpRequestException extends RuntimeException {

    public HttpRequestException(String message) {
        super(message);
    }
}
