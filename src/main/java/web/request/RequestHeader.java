package web.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
	private static final String HEADER_DELIMITER = ": ";
	private static final String CONTENT_LENGTH = "Content-Length";

	private Map<String, String> headerInfo;

	public RequestHeader(Map<String, String> headerInfo) {
		this.headerInfo = headerInfo;
	}

	public static RequestHeader of(BufferedReader br) throws IOException {
		Map<String, String> header = new HashMap<>();

		String line = br.readLine();

		while (!"".equals(line)) {
			String[] headerInfos = line.split(HEADER_DELIMITER);
			header.put(headerInfos[0], headerInfos[1]);
			line = br.readLine();
		}

		return new RequestHeader(header);
	}

	public String get(String key) {
		return headerInfo.get(key);
	}

	public int getContentLength() {
		return Integer.parseInt(headerInfo.get(CONTENT_LENGTH));
	}
}
