package webserver.process.get;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Function;

import utils.FileIoUtils;
import webserver.exception.NotExistUrlException;
import webserver.http.HttpRequest;

public class GetUrlProcessor implements Function<HttpRequest, byte[]> {

	@Override
	public byte[] apply(HttpRequest httpRequest) {
		if (httpRequest.isSameUrl("/index.html")) {
			try {
				return FileIoUtils.loadFileFromClasspath("./templates/index.html");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if (httpRequest.isSameUrl("/user/form.html")) {
			try {
				return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
		throw new NotExistUrlException();
	}
}
