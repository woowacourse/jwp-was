package exception;

public class RequestParameterNotFoundException extends RuntimeException {
    private static final String NOT_FOUND_PARAMS_MESSAGE = "해당 값을 인자 목록에서 찾을 수 없습니다";

    public RequestParameterNotFoundException() {
        super(NOT_FOUND_PARAMS_MESSAGE);
    }

    public RequestParameterNotFoundException(String key) {
        super(key + " : " + NOT_FOUND_PARAMS_MESSAGE);
    }
}
