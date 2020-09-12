package webserver.process.get;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.function.Function;

import model.User;
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
		} else if (httpRequest.containsInUrl("/user/create")) {
			User user = UserParser.parseUser(httpRequest.getUrl());

			System.out.println("여기 잘 들어오는가 ? ");

			return "create user success".getBytes();
		}
		throw new NotExistUrlException();
	}
}
