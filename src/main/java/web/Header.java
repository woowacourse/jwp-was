package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Header {
	private static final String HEADER_DELIMITER = ": ";
	private static final String CONTENT_LENGTH = "Content-Length";

	private Map<String, String> headerInfo;

	public Header(Map<String, String> headerInfo) {
		this.headerInfo = headerInfo;
	}

	public static Header of(BufferedReader br) throws IOException {
		Map<String, String> header = new HashMap<>();

		String line = br.readLine();

		while (!"".equals(line)) {
			String[] s1 = line.split(HEADER_DELIMITER);
			header.put(s1[0], s1[1]);
			line = br.readLine();
		}

		return new Header(header);
	}

	public String get(String key) {
		return headerInfo.get(key);
	}

	public int getContentLength() {
		return Integer.parseInt(headerInfo.get(CONTENT_LENGTH));
	}
}
