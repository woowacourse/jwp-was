package application.service;

import application.db.DataBase;
import application.model.User;
import application.model.UserLoginException;
import webserver.http.request.HttpRequest;

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

	public static User login(HttpRequest request) {
		User user = DataBase.findUserById(request.getHttpBodyValueOf("userId"));

		if (user == null) {
			throw new UserLoginException("해당되는 id의 사용자가 없습니다.");
		}

		if (!request.getHttpBodyValueOf("password").equals(user.getPassword())) {
			throw new UserLoginException("올바르지 않은 비밀번호입니다.");
		}

		return user;
	}
}
