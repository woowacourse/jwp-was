package webserver.process;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;

public class PostUrlProcessor implements Function<String, byte[]> {

	@Override
	public byte[] apply(String url) {
		throw new NotExistUrlException();
	}
}
