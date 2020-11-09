package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ExtractUtils {
	private static final String PARAM_DELIMITER = "&";
	private static final String ELEMENT_DELIMITER = "=";

	public static Map<String, String> parseRequestBody(String body) throws UnsupportedEncodingException {
		String[] infos = body.split(PARAM_DELIMITER);
		Map<String, String> parsedBody = new HashMap<>();

		for (String info : infos) {
			String element = info.split(ELEMENT_DELIMITER)[0];
			String content = URLDecoder.decode(info.split(ELEMENT_DELIMITER)[1], "UTF-8");
			parsedBody.put(element, content);
		}

		return parsedBody;
	}
}
