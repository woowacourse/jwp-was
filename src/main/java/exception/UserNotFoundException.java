package exception;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND_MESSAGE = "해당 유저를 찾을 수 없습니다. id : ";

    public UserNotFoundException(String id) {
        super(USER_NOT_FOUND_MESSAGE + id);
    }
}
