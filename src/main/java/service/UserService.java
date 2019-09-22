package service;

import java.util.Map;

import db.DataBase;
import model.User;

public class UserService {
	public static void saveUser(Map<String, String> userInfo) {
		User user = new User(userInfo.get("userId"), userInfo.get("password"),
				userInfo.get("name"), userInfo.get("email"));
		DataBase.addUser(user);
	}
}
