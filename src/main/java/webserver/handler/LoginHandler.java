package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import db.DataBase;
import model.User;
import utils.ExtractUtils;
import webserver.HttpCookie;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.HttpSession;
import webserver.LoginStatus;
import webserver.SessionStorage;

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
		HttpCookie httpCookie = new HttpCookie(httpRequest.getHeader("Cookie"));
		HttpSession httpSession = SessionStorage.getSession(httpCookie.getCookie("sessionId"));
		httpSession.setAttribute("logined", true);

		HttpResponse.responseLogin302Header(dos, loginStatus, httpSession, logger);
	}

	@Override
	public boolean canHandle(String url) {
		return USER_LOGIN_URL.equals(url);
	}
}
