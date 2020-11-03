package application.service;

import java.util.Collections;
import java.util.List;

import application.db.DataBase;
import application.model.User;
import application.model.UserLoginException;
import webserver.http.request.HttpRequest;
import webserver.session.HttpSession;
import webserver.session.SessionStorage;

public class UserService {
	public static void create(HttpRequest request) {
		User user = new User(
			request.getHttpBodyValueOf("userId"),
			request.getHttpBodyValueOf("password"),
			request.getHttpBodyValueOf("name"),
			request.getHttpBodyValueOf("email")
		);
		DataBase.addUser(user);
	}

	public static List<User> findAll() {
		return Collections.unmodifiableList(DataBase.findAll());
	}

	public static String login(HttpRequest request) {
		User user = DataBase.findUserById(request.getHttpBodyValueOf("userId"));

		if (user == null) {
			throw new UserLoginException("해당되는 id의 사용자가 없습니다.");
		}

		if (!request.getHttpBodyValueOf("password").equals(user.getPassword())) {
			throw new UserLoginException("올바르지 않은 비밀번호입니다.");
		}

		if (isLogin(request)) {
			return getSessionId(request);
		}

		String sessionId = SessionStorage.create("userId", user.getUserId());
		SessionStorage.loginBy(sessionId);
		return sessionId;
	}

	private static String getSessionId(HttpRequest request) {
		String cookie = request.getHttpHeaderParameterOf("Cookie");
		return cookie.substring(cookie.indexOf("sessionId=") + 10, cookie.indexOf("sessionId=") + 46);
	}

	public static boolean isLogin(HttpRequest request) {
		String sessionId = getSessionId(request);
		HttpSession httpSession = SessionStorage.findBy(sessionId);
		return (boolean)httpSession.getAttribute("logined");
	}
}
