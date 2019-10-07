package webserver.exception;

public class FailedRedirectException extends RuntimeException {
	public FailedRedirectException() {
		super("요청을 Redirect하는데 실패했습니다.");
	}
}
