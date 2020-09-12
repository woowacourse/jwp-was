package webserver.process.patch;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;
import webserver.http.HttpRequest;

public class PatchUrlProcessor implements Function<HttpRequest, byte[]> {

	@Override
	public byte[] apply(HttpRequest httpRequest) {
		throw new NotExistUrlException();
	}
}
