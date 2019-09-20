package http.request.exception;

public class ParameterNotFoundException extends RuntimeException {

    public static final String PARAMETER_NOT_FOUND_EXCEPTION_MESSAGE = "존재하지 않는 Parameter 입니다.";

    public ParameterNotFoundException() {
        super(PARAMETER_NOT_FOUND_EXCEPTION_MESSAGE);
    }
}
