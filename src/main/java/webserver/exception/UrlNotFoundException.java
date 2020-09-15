package webserver.exception;

public class UrlNotFoundException extends IllegalArgumentException {

    public UrlNotFoundException(String path) {
        super("등록되지 않은 경로입니다. Path = " + path);
    }
}
