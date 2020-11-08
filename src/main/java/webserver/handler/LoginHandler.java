package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import db.DataBase;
import model.User;
import utils.ExtractUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class LoginHandler extends Handler {
	private static final String USER_LOGIN_URL = "/user/login";
	private static final String LOGIN_SUCCESS_REDIRECT_URL = "/index.html";
	private static final String LOGIN_FAILED_REDIRECT_URL = "/user/login_failed.html";

	@Override
	public void handleRequest(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
		Map<String, String> loginInfo = ExtractUtils.parseRequestBody(httpRequest.getBody());
		String userId = loginInfo.get("userId");
		String password = loginInfo.get("password");
		User user = Optional.ofNullable(DataBase.findUserById(userId))
			.orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

		if (user.validatePassword(password)) {
			HttpResponse.response302Header(dos, LOGIN_SUCCESS_REDIRECT_URL, logger);
		} else {
			HttpResponse.response302Header(dos, LOGIN_FAILED_REDIRECT_URL, logger);
		}
	}

	@Override
	public boolean canHandle(String url) {
		return USER_LOGIN_URL.equals(url);
	}
}
