package controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerContainer {
	private static Map<String, Controller> controllers = new HashMap<>();

	static {
		controllers.put("/user/create", new CreateUserController());
		controllers.put("/", new FileController());
	}

	public static Controller getController(String uri) {
		return controllers.get(uri);
	}
}
