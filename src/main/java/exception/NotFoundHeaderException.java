package exception;

public class NotFoundHeaderException extends RuntimeException {
	public NotFoundHeaderException() {
		super("해당 헤더를 찾을 수 없습니다.");
	}
}
