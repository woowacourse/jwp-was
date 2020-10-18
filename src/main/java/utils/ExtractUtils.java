package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class ExtractUtils {
	private static final String HEADER_DELIMITER = ": ";
	private static final String FIRST_HEADER_DELIMITER = " ";
	private static final String URL_DELIMITER = "\\?";
	private static final String PARAM_DELIMITER = "&";
	private static final String ELEMENT_DELIMITER = "=";
	private static final int URL_WITH_PARAMS_INDEX = 1;
	private static final int URL_INDEX = 0;
	private static final int PARAMS_INDEX = 1;
	private static final int METHOD_INDEX = 0;

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

	public static void extractFirstHeader(BufferedReader br, Map<String, String> headers) throws IOException {
		String startLine = br.readLine();
		String[] splitStartLine = startLine.split(FIRST_HEADER_DELIMITER);
		String[] splitUrl = splitStartLine[URL_WITH_PARAMS_INDEX].split(URL_DELIMITER);

		headers.put("method", splitStartLine[METHOD_INDEX]);
		headers.put("url", splitUrl[URL_INDEX]);
		if (splitUrl.length > 1) {
			extractParams(splitUrl[PARAMS_INDEX], headers);
		}
	}

	private static void extractParams(String startLineParams, Map<String, String> headers) {
		String[] params = startLineParams.split(PARAM_DELIMITER);

		for (String param : params) {
			String element = param.split(ELEMENT_DELIMITER)[0];
			String content = param.split(ELEMENT_DELIMITER)[1];
			headers.put(element, content);
		}
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
