package webserver.exception;

public class WrongUriException extends RuntimeException {
	public WrongUriException() {
		super("잘못된 Uri입니다.");
	}
}
