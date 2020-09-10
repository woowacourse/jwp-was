package exception;

public class InvalidFilePathException extends RuntimeException {
    private static final String INVALID_FILE_PATH_MASSAGE = "잘못된 파일 경로입니다";

    public InvalidFilePathException() {
        this(INVALID_FILE_PATH_MASSAGE);
    }

    public InvalidFilePathException(String message) {
        super(message);
    }
}
