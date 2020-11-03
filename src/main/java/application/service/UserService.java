package application.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import application.db.DataBase;
import application.model.User;
import application.model.UserLoginException;
import webserver.http.request.HttpRequest;
import webserver.session.HttpSession;
import webserver.session.SessionStorage;

public class UserService {
	private static final String USER_ID = "userId";
	private static final String SESSION_ID = "sessionId=";
	private static final String PASSWORD = "password";
	private static final int SESSION_ID_KEY_LENGTH = 10;
	private static final int SESSION_ID_LENGTH = 46;

	public static void create(HttpRequest request) {
		User user = new User(
			request.getHttpBodyValueOf(USER_ID),
			request.getHttpBodyValueOf(PASSWORD),
			request.getHttpBodyValueOf("name"),
			request.getHttpBodyValueOf("email")
		);
		DataBase.addUser(user);
	}

	public static List<User> findAll() {
		return Collections.unmodifiableList(DataBase.findAll());
	}

	public static String login(HttpRequest request) {
		User user = DataBase.findUserById(request.getHttpBodyValueOf(USER_ID));

		if (user == null) {
			throw new UserLoginException("해당되는 id의 사용자가 없습니다.");
		}

		if (!Objects.equals(request.getHttpBodyValueOf(PASSWORD), user.getPassword())) {
			throw new UserLoginException("올바르지 않은 비밀번호입니다.");
		}

		if (isLogin(request)) {
			return getSessionId(request);
		}

		String sessionId = SessionStorage.create(USER_ID, user.getUserId());
		SessionStorage.loginBy(sessionId);
		return sessionId;
	}

	public static boolean isLogin(HttpRequest request) {
		if (request.hasHttpHeaderParameterOf("Cookie")) {
			String sessionId = getSessionId(request);
			HttpSession httpSession = SessionStorage.findBy(sessionId);
			return (boolean)httpSession.getAttribute("logined");
		}
		return false;
	}

	private static String getSessionId(HttpRequest request) {
		String cookie = request.getHttpHeaderParameterOf("Cookie");
		return cookie.substring(cookie.indexOf(SESSION_ID) + SESSION_ID_KEY_LENGTH,
			cookie.indexOf(SESSION_ID) + SESSION_ID_LENGTH);
	}
}
