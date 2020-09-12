package webserver.process.post;

import java.util.function.Function;

import model.User;
import webserver.exception.NotExistUrlException;
import webserver.http.HttpRequest;
import webserver.process.get.UserParser;

public class PostUrlProcessor implements Function<HttpRequest, byte[]> {

	@Override
	public byte[] apply(HttpRequest httpRequest) {
		if (httpRequest.isSameUrl("/user/create")) {
			User user = UserParser.parseUser(httpRequest.getHttpBody());

			return String.format("create user : userId=%s", user.getUserId()).getBytes();
		}
		throw new NotExistUrlException();
	}
}
