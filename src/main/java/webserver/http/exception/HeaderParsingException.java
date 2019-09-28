package webserver.http.exception;

public class HeaderParsingException extends RuntimeException {
    public HeaderParsingException() {
        super("HeaderLines 중에 잘못된 구조로 있습니다.");
    }
}
