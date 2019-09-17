package exception;

public class NotFoundRequestElementException extends RuntimeException {
    public NotFoundRequestElementException() {
        super("해당 요청의 정보를 찾을 수 없습니다.");
    }
}
