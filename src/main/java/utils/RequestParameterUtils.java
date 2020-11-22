package utils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import web.request.RequestLine;

public class RequestParameterUtils {
	private static final String QUESTION_MARK = "?";
	private static final String QUESTION_MARK_DELIMITER = "\\?";
	private static final String AND_MARK = "&";
	private static final String EQUALS_MARK_DELIMITER = "=";

	public static Map<String, String> extractRequestParameters(RequestLine requestLine) {
		Map<String, String> signUpRequestData = new HashMap<>();
		String path = requestLine.getPath();

		if (!path.contains(QUESTION_MARK)) {
			return new HashMap<>();
		}

		String[] requestParameters = path.split(QUESTION_MARK_DELIMITER)[1]
			.split(AND_MARK);

		for (String parameter : requestParameters) {
			String[] data = URLDecoder.decode(parameter).split(EQUALS_MARK_DELIMITER);
			signUpRequestData.put(data[0], data[1]);
		}

		requestLine.setPath(path.split(QUESTION_MARK_DELIMITER)[0]);

		return signUpRequestData;
	}

	public static Map<String, String> extractBodyParameters(String body) {
		Map<String, String> signUpRequestData = new HashMap<>();
		String[] requestParameters = body.split(AND_MARK);
		for (String parameter : requestParameters) {
			String[] data = URLDecoder.decode(parameter).split(EQUALS_MARK_DELIMITER);
			signUpRequestData.put(data[0], data[1]);
		}
		return signUpRequestData;
	}
}
