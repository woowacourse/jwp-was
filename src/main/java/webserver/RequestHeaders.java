package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeaders {
	private static final String HEADER_DELIMITER = ": ";

	private final Map<String, String> headers;

	private RequestHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public static RequestHeaders of(BufferedReader br) throws IOException {
		Map<String, String> headers = extractHeaders(br);

		return new RequestHeaders(headers);
	}

	private static Map<String, String> extractHeaders(BufferedReader br) throws IOException {
		Map<String, String> headers = new HashMap<>();

		String line = br.readLine();
		while (!"".equals(line)) {
			if (line == null) {
				break;
			}
			String[] header = line.split(HEADER_DELIMITER);
			headers.put(header[0], header[1]);
			line = br.readLine();
		}

		return headers;
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

	public String getHeader(String header) {
		return headers.get(header);
	}
}
