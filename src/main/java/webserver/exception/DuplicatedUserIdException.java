package webserver.exception;

public class DuplicatedUserIdException extends BadRequestException {
    private static final String DUPLICATED_USER_ID_EXCEPTION_MESSAGE_FORMAT = "중복된 아이디로는 가입하실 수 없습니다. : %s";

    public DuplicatedUserIdException(String userId) {
        super(String.format(DUPLICATED_USER_ID_EXCEPTION_MESSAGE_FORMAT, userId));
    }
}
