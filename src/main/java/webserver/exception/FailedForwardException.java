package webserver.exception;

public class FailedForwardException extends RuntimeException {
	public FailedForwardException() {
		super("요청을 Forward하는데 실패했습니다.");
	}
}
