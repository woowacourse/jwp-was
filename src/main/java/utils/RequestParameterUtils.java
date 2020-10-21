package utils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParameterUtils {
	private static final String QUESTION_MARK = "?";
	private static final String QUESTION_MARK_DELIMITER = "\\?";

	public static Map<String, String> extractParametersIfGetMethod(String path) {
		Map<String, String> signUpRequestData = new HashMap<>();

		if(!path.contains(QUESTION_MARK)) {
			return new HashMap<>();
		}

		String[] requestParameters = path.split(QUESTION_MARK_DELIMITER)[1]
			.split("&");

		for (String parameter : requestParameters) {
			String[] data = URLDecoder.decode(parameter).split("=");
			signUpRequestData.put(data[0], data[1]);
		}

		return signUpRequestData;
	}

	public static Map<String, String> extractParametersIfPostMethod(String body) {
		Map<String, String> signUpRequestData = new HashMap<>();
		String[] requestParameters = body.split("&");
		for (String parameter : requestParameters) {
			String[] data = URLDecoder.decode(parameter).split("=");
			signUpRequestData.put(data[0], data[1]);
		}
		return signUpRequestData;
	}
}
