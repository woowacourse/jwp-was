package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ExtractUtils {
	private static final String HEADER_DELIMITER = ": ";
	private static final String PARAM_DELIMITER = "&";
	private static final String ELEMENT_DELIMITER = "=";

	public static Map<String, String> extractUserInfo(String body) throws UnsupportedEncodingException {
		String[] infos = body.split(PARAM_DELIMITER);
		Map<String, String> userInfo = new HashMap<>();

		for (String info : infos) {
			String element = info.split(ELEMENT_DELIMITER)[0];
			String content = URLDecoder.decode(info.split(ELEMENT_DELIMITER)[1], "UTF-8");
			userInfo.put(element, content);
		}

		return userInfo;
	}

	public static void extractExtraHeaders(BufferedReader br, Map<String, String> headers) throws IOException {
		String line = br.readLine();
		while (!"".equals(line)) {
			if (line == null) {
				break;
			}
			String[] header = line.split(HEADER_DELIMITER);
			headers.put(header[0], header[1]);
			line = br.readLine();
		}
	}
}
