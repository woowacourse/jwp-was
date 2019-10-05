package webserver.exception;

public class NotFoundSessionException extends RuntimeException {
	public NotFoundSessionException() {
		super("해당 세션 id를 찾을 수 없습니다.");
	}
}
