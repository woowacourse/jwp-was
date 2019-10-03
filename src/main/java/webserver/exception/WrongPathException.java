package webserver.exception;

public class WrongPathException extends RuntimeException {
	public WrongPathException() {
		super("잘못된 경로입니다.");
	}
}
