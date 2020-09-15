package utils;

public class RequestPathUtil {
	public static String extract(String request) {
		String path = request.split(" ")[1];
		if (path.endsWith(".html") || path.endsWith(".ico")) {
			return "./templates" + path;
		}

		return "./static" + path;
	}
}
