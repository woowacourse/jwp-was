package webserver.process.delete;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;
import webserver.http.HttpRequest;

public class DeleteUrlProcessor implements Function<HttpRequest, byte[]> {

	@Override
	public byte[] apply(HttpRequest httpRequest) {
		throw new NotExistUrlException();
	}
}
