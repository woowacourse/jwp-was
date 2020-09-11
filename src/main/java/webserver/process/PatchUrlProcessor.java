package webserver.process;

import java.util.function.Function;

import webserver.exception.NotExistUrlException;

public class PatchUrlProcessor implements Function<String, byte[]> {

	@Override
	public byte[] apply(String url) {
		throw new NotExistUrlException();
	}
}
