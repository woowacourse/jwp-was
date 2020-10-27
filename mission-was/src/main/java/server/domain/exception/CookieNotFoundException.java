package server.domain.exception;

public class CookieNotFoundException extends IllegalArgumentException {

    public CookieNotFoundException(String key) {
        super("존재하지 않는 쿠키입니다. key = " + key);
    }
}
