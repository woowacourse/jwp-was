package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utils.ExtractUtils;

public class RequestHeaders {
	private static final String POST_METHOD = "POST";

	private final Map<String, String> headers;

	private RequestHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public static RequestHeaders of(BufferedReader br) throws IOException {
		Map<String, String> headers = new HashMap<>();

		ExtractUtils.extractFirstHeader(br, headers);
		ExtractUtils.extractExtraHeaders(br, headers);

		return new RequestHeaders(headers);
	}

	public int getContentSize() {
		String contentLength = headers.get("Content-Length");
		int length = 0;
		try {
			length = Integer.parseInt(contentLength);
		} catch (NumberFormatException e) {
			System.err.println("Content-Length가 숫자가 아닙니다.");
		}
		return length;
	}

	public boolean isPostMethod() {
		return POST_METHOD.equals(headers.get("method"));
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getParameter(String param) {
		return headers.get(param);
	}
}
