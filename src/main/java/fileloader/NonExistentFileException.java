package fileloader;

public class NonExistentFileException extends RuntimeException {
    private static final String ERROR_MESSAGE_FORMAT = "%s는 존재하지 않는 파일입니다.";

    public NonExistentFileException(String message) {
        super(String.format(ERROR_MESSAGE_FORMAT, message));
    }
}
