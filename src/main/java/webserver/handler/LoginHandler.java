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
import webserver.LoginStatus;

public class LoginHandler extends Handler {
	private static final String USER_LOGIN_URL = "/user/login";

	@Override
	public void handleRequest(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
		Map<String, String> loginInfo = ExtractUtils.parseRequestBody(httpRequest.getBody());
		String userId = loginInfo.get("userId");
		String password = loginInfo.get("password");
		User user = Optional.ofNullable(DataBase.findUserById(userId))
			.orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
		LoginStatus loginStatus = LoginStatus.of(user.validatePassword(password));

		HttpResponse.responseLogin302Header(dos, loginStatus, logger);
	}

	@Override
	public boolean canHandle(String url) {
		return USER_LOGIN_URL.equals(url);
	}
}
