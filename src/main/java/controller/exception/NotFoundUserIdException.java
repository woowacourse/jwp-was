package controller.exception;

public class NotFoundUserIdException extends RuntimeException {
    private static final String NOT_FOUND_USER_ID_EXCEPTION_MESSAGE = "해당 아이디의 유저를 찾을 수 없습니다.";

    public NotFoundUserIdException() {
        super(NOT_FOUND_USER_ID_EXCEPTION_MESSAGE);
    }
}