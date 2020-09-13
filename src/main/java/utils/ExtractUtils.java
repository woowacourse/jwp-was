package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExtractUtils {
	private static final String PATH_DELIMITER = "\\?";
	private static final String PARAM_DELIMITER = "&";
	private static final String ELEMENT_DELIMITER = "=";

	public static Map<String, String> extractUserInfo(String path) throws UnsupportedEncodingException {
		String params = path.split(PATH_DELIMITER)[1];
		String[] infos = params.split(PARAM_DELIMITER);
		Map<String, String> userInfo = new HashMap<>();

		Arrays.stream(infos).forEach(System.out::println);

		for (String info : infos) {
			String element = info.split(ELEMENT_DELIMITER)[0];
			String content = URLDecoder.decode(info.split(ELEMENT_DELIMITER)[1], "UTF-8");
			userInfo.put(element, content);
		}

		return userInfo;
	}
}
