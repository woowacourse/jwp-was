package webserver.process.post;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;
import webserver.http.HttpRequest;

public class PostUrlProcessor implements Function<HttpRequest, byte[]> {

	@Override
	public byte[] apply(HttpRequest httpRequest) {
		throw new NotExistUrlException();
	}
}
