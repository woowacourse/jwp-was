package http.exception;

public class NotFoundParameterException extends RuntimeException {
    private static final String NOT_FOUND_PARAMETER_EXCEPTION_MESSAGE = "찾을 수 없는 쿼리 파라미터입니다.";

    public NotFoundParameterException() {
        super(NOT_FOUND_PARAMETER_EXCEPTION_MESSAGE);
    }
}
