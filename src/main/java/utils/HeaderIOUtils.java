package utils;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.HttpRequestStartLine;
import webserver.http.request.header.HttpRequestHttpHeaders;

public class HeaderIOUtils {

	public static final int FIRST_INDEX_EXCLUDING_START_LINE = 1;

	public static HttpRequestHttpHeaders parseHttpHeaders(String httpHeader) {
		Map<String, String> headers = new HashMap<>();

		String[] headerLine = httpHeader.split("\n");

		parseExceptStartLine(headers, headerLine);

		return new HttpRequestHttpHeaders(headers);
	}

	private static void parseExceptStartLine(Map<String, String> headers, String[] headerLine) {
		for (int i = FIRST_INDEX_EXCLUDING_START_LINE; i < headerLine.length; i++) {
			String[] headerPair = headerLine[i].split(": ");
			headers.put(headerPair[0], headerPair[1]);
		}
	}

	public static HttpRequestStartLine parseStartLine(String httpHeader) {
		String startLine = httpHeader.split("\n")[0];
		String[] startLineHeaders = startLine.split(" ");
		return new HttpRequestStartLine(startLineHeaders[0], startLineHeaders[1], startLineHeaders[2]);
	}
}
