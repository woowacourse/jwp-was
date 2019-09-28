package controller;

import controller.exception.ControllerNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ControllerContainer {
	private static Map<String, Controller> controllers = new HashMap<>();

	static {
		controllers.put(IndexController.PATH, new IndexController());
		controllers.put(CreateUserController.PATH, new CreateUserController());
		controllers.put(LoginUserController.PATH, new LoginUserController());
		controllers.put("file", new FileController());
	}

	public static Controller getController(boolean isContainExtension, String uri) {
		if (isContainExtension) {
			return controllers.get("file");
		}

		return Optional.ofNullable(controllers.get(uri))
				.orElseThrow(() -> new ControllerNotFoundException("not found" + uri))
				;
	}
}
