package exception;

import java.util.Arrays;

public class InvalidCookieException extends RuntimeException {
    public InvalidCookieException(Object... object) {
        super("처리할 수 없는 Cookie 값입니다! -> " + Arrays.toString(object));
    }
}
