package exceptions;

public class InvalidFilePathException extends IllegalArgumentException {

    private static final String INVALID_FILE_PATH_MESSAGE = "잘못된 경로입니다.";

    public InvalidFilePathException() {
        super(INVALID_FILE_PATH_MESSAGE);
    }
}
