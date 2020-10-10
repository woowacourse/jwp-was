package exception;

public class InvalidSessionAttributeException extends RuntimeException {
    public InvalidSessionAttributeException(String sessionAttribute) {
        super("처리할 수 없는 Session 속성입니다! -> " + sessionAttribute);
    }
}
