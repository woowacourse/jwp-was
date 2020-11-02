package application.model;

public class UserDto {
	private final String userId;
	private final String name;
	private final String email;

	public UserDto(String userId, String name, String email) {
		this.userId = userId;
		this.name = name;
		this.email = email;
	}

	public UserDto(User user) {
		this(user.getUserId(), user.getName(), user.getEmail());
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}