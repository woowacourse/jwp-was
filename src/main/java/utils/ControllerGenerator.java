package utils;

import java.util.HashMap;
import java.util.Map;

import controller.CreateUserController;
import controller.ResourceController;
import controller.Controller;

public class ControllerGenerator {
	private static Map<String, Controller> controllers = new HashMap<>();

	static {
		controllers.put("resource", new ResourceController());
		controllers.put("/user/create", new CreateUserController());
	}

	public static Controller generateController(String path) {
		if(path.contains(".")) {
			return controllers.get("resource");
		}
		return controllers.get(path);
	}
}
