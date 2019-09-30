package http.exception;

public class MediaTypeNotDefinedException extends HttpException {
    public static final String MESSAGE = "해당하는 Media Type이 정의되지 않았습니다.";

    public MediaTypeNotDefinedException() {
        super(MESSAGE);
    }
}
