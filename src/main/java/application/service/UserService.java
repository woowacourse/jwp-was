package application.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import application.db.DataBase;
import webserver.exception.FailedUserJoinException;
import application.model.User;

public class UserService {
	public static User saveUser(Map<String, String> userInfo) {
		try {
			User user = new User(userInfo.get("userId"), userInfo.get("password"),
					userInfo.get("name"), URLDecoder.decode(userInfo.get("email"), "utf-8"));
			DataBase.addUser(user);
			return user;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		throw new FailedUserJoinException();
	}

	public static List<User> findAllUsers() {
		return new ArrayList<>(DataBase.findAll());
	}

	public boolean login(String userId, String password) {
		User user = DataBase.findUserById(userId);
		if (Objects.nonNull(user) && password.equals(user.getPassword())) {
			return true;
		}
		return false;
	}
}
