package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;
import utils.RequestPathUtil;

public class HttpRequest {
	public static final String SPACE_DELIMITER = " ";
	public static final String QUESTION_MARK_DELIMITER = "\\?";
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String HEADER_DELIMITER = ": ";
	private final HttpMethod method;
	private final String path;
	private final Map<String, String> header;
	private final Map<String, String> parameter;

	public HttpRequest(HttpMethod method, String path, Map<String, String> header,
		Map<String, String> parameter) {
		this.method = method;
		this.path = path;
		this.header = header;
		this.parameter = parameter;
	}

	public static HttpRequest of(BufferedReader br) throws IOException {
		String[] requestHeader = br.readLine().split(SPACE_DELIMITER);
		HttpMethod method = HttpMethod.valueOf(requestHeader[0]);
		String path = requestHeader[1];

		Map<String, String> header = makeHeader(br);

		Map<String, String> parameter = new HashMap<>();
		if (method.equals(HttpMethod.GET) && path.contains(QUESTION_MARK_DELIMITER)) {
			String requestParameter = path.split(QUESTION_MARK_DELIMITER)[1];
			parameter = RequestPathUtil.extractParameters(requestParameter);
		} else if (method.equals(HttpMethod.POST)) {
			int contentLength = Integer.parseInt(header.get(CONTENT_LENGTH));
			String body = IOUtils.readData(br, contentLength);
			parameter = RequestPathUtil.extractParameters(body);
		}

		return new HttpRequest(method, path, header, parameter);
	}

	private static Map<String, String> makeHeader(BufferedReader br) throws IOException {
		Map<String, String> header = new HashMap<>();

		String line = br.readLine();

		while (!"".equals(line)) {
			String[] s1 = line.split(HEADER_DELIMITER);
			header.put(s1[0], s1[1]);
			line = br.readLine();
		}

		return header;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public String getHeader(String key) {
		return header.get(key);
	}

	public String getParameter(String key) {
		return parameter.get(key);
	}
}
