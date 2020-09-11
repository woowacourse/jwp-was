package webserver.process.get;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import model.User;

public class UserParser {

	public static User parseUser(String url) {
		String[] requestParams = url.substring(url.indexOf("?") + 1)
			.split("&");

		List<String> params = Arrays.stream(requestParams)
			.map(param -> param.split("="))
			.map(paramValue -> paramValue[1])
			.collect(Collectors.toList());

		String userId = params.get(0);
		String password = params.get(1);
		String name = params.get(2);
		String email = params.get(3);

		return new User(userId, password, name, email);
	}
}
