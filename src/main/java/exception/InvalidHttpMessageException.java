package exception;

import java.util.Arrays;

public class InvalidHttpMessageException extends RuntimeException {
    public InvalidHttpMessageException(Object... object) {
        super("잘못된 형식의 HTTP Message입니다! -> " + Arrays.toString(object));
    }
}