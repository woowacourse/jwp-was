package db.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User를 찾을 수 없습니다.");
    }
}
