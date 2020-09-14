package exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String parameter) {
        super("정상적인 Parameter 형식이 아닙니다! -> " + parameter);
    }
}
