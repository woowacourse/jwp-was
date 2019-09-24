package exceptions;

public class NotFoundFileException extends RuntimeException {
    public NotFoundFileException(String filePath) {
        super("없는 경로 입니다: " + filePath);
    }
}
