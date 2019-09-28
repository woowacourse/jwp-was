package service;

import java.util.Map;
import java.util.Objects;

import db.DataBase;
import model.User;

public class UserService {
	public static User saveUser(Map<String, String> userInfo) {
		User user = new User(userInfo.get("userId"), userInfo.get("password"),
				userInfo.get("name"), userInfo.get("email"));
		DataBase.addUser(user);
		return user;
	}

	public boolean login(String userId, String password) {
		User user = DataBase.findUserById(userId);
		if(Objects.nonNull(user) && password.equals(user.getPassword())) {
			return true;
		}
		return false;
	}
}
