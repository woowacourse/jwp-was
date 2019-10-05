package webserver.exception;

public class NotFoundElementException extends RuntimeException {
	public NotFoundElementException() {
		super("해당 요소를 찾을 수 없습니다.");
	}
}
