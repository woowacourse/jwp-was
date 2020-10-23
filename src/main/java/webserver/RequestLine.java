package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestLine {
	private static final String POST_METHOD = "POST";
	private static final String REQUEST_LINE_DELIMITER = " ";
	private static final String URL_DELIMITER = "\\?";
	private static final String PARAM_DELIMITER = "&";
	private static final String ELEMENT_DELIMITER = "=";
	private static final String METHOD = "method";
	private static final String URL = "url";
	private static final String PARAMETERS = "parameters";
	private static final int URL_WITH_PARAMS_INDEX = 1;
	private static final int URL_INDEX = 0;
	private static final int PARAMS_INDEX = 1;
	private static final int METHOD_INDEX = 0;

	private final String method;
	private final String url;
	private final Map<String, String> parameters;

	private RequestLine(Map<String, String> requestLine, Map<String, String> parameters) {
		this.method = requestLine.get(METHOD);
		this.url = requestLine.get(URL);
		this.parameters = parameters;
	}

	public static RequestLine of(BufferedReader br) throws IOException {
		Map<String, String> requestLine = extractRequestLine(br);
		Map<String, String> parameters = new HashMap<>();

		if (requestLine.containsKey(PARAMETERS)) {
			extractParams(requestLine.get(PARAMETERS), parameters);
		}

		return new RequestLine(requestLine, parameters);
	}

	private static Map<String, String> extractRequestLine(BufferedReader br) throws IOException {
		Map<String, String> requestLine = new HashMap<>();
		String startLine = br.readLine();
		String[] splitRequestLine = startLine.split(REQUEST_LINE_DELIMITER);
		String[] splitUrl = splitRequestLine[URL_WITH_PARAMS_INDEX].split(URL_DELIMITER);

		requestLine.put(METHOD, splitRequestLine[METHOD_INDEX]);
		requestLine.put(URL, splitUrl[URL_INDEX]);
		if (splitUrl.length > 1) {
			requestLine.put(PARAMETERS, splitUrl[PARAMS_INDEX]);
		}
		return requestLine;
	}

	private static void extractParams(String requestLineParams, Map<String, String> parameters) {
		String[] params = requestLineParams.split(PARAM_DELIMITER);

		for (String param : params) {
			String element = param.split(ELEMENT_DELIMITER)[0];
			String content = param.split(ELEMENT_DELIMITER)[1];
			parameters.put(element, content);
		}
	}

	public boolean isPostMethod() {
		return POST_METHOD.equals(method);
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getParameter(String param) {
		return parameters.get(param);
	}
}
