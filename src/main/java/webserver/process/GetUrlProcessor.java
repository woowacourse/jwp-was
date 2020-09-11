package webserver.process;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Function;

import utils.FileIoUtils;
import webserver.exception.NotExistUrlException;

public class GetUrlProcessor implements Function<String, byte[]> {

	@Override
	public byte[] apply(String url) {
		if (url.equals("/index.html")) {
			try {
				return FileIoUtils.loadFileFromClasspath("./templates/index.html");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		throw new NotExistUrlException();
	}
}
