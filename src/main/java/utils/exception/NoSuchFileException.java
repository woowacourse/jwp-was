package utils.exception;

public class NoSuchFileException extends RuntimeException {
    private static final String NOT_FOUND_FILE_MESSAGE = "해당 파일이 존재하지 않습니다.";

    public NoSuchFileException() {
        super(NOT_FOUND_FILE_MESSAGE);
    }

    public NoSuchFileException(String message) {
        super(message);
    }
}
