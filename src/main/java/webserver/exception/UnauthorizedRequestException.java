package webserver.exception;

public class UnauthorizedRequestException extends RuntimeException {
	public UnauthorizedRequestException() {
		super("허락되지 않은 요청입니다.");
	}
}
