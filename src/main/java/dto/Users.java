package dto;

import java.util.List;

import model.User;

public class Users {
	private List<User> users;

	public Users(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}
}
