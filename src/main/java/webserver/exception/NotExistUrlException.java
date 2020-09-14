package webserver.exception;

public class NotExistUrlException extends IllegalArgumentException {

	public NotExistUrlException(String invalidUrl) {
		super("잘못된 URL을 가지고 있습니다. url = " + invalidUrl);
	}
}
