package webserver.exception;

public class FailResponseException extends RuntimeException {
	public FailResponseException() {
		super("해당 응답을 반환하는데 실패했습니다.");
	}
}
