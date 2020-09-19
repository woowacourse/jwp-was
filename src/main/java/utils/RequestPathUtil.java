package utils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestPathUtil {

	public static final String HTML_FILE_TYPE = ".html";
	public static final String ICO_FILE_TYPE = ".ico";
	public static final String TEMPLATES_PATH = "./templates";
	public static final String STATIC_PATH = "./static";

	public static String extractFilePath(String path) {
		if (path.endsWith(HTML_FILE_TYPE) || path.endsWith(ICO_FILE_TYPE)) {
			return TEMPLATES_PATH + path;
		}

		return STATIC_PATH + path;
	}

	public static Map<String, String> extractParameters(String requestData) {
		Map<String, String> signUpRequestData = new HashMap<>();
		String[] requestParemeters = requestData.split("&");
		for (String parameter : requestParemeters) {
			String[] data = URLDecoder.decode(parameter).split("=");
			signUpRequestData.put(data[0], data[1]);
		}
		return signUpRequestData;
	}
}
