package webserver.process.put;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;
import webserver.http.HttpRequest;

public class PutUrlProcessor implements Function<HttpRequest, byte[]> {

	@Override
	public byte[] apply(HttpRequest httpRequest) {
		throw new NotExistUrlException();
	}
}
