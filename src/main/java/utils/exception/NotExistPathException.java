package utils.exception;

public class NotExistPathException extends RuntimeException {
    public NotExistPathException() {
        super("존재하지 않는 Path 입니다.");
    }
}