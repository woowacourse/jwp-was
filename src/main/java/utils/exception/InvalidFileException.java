package utils.exception;

public class InvalidFileException extends RuntimeException {
    private static final String MESSAGE = "파일이 존재하지 않습니다.";

    public InvalidFileException() {
        super(MESSAGE);
    }
}
