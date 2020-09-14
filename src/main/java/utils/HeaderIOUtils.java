package utils;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.HttpRequestStartLine;
import webserver.http.request.header.HttpRequestHttpHeaders;

public class HeaderIOUtils {

	public static final int FIRST_INDEX_EXCLUDING_START_LINE = 1;
	public static final String HEADER_REGEX = ": ";
	private static final String START_LINE_REGEX = " ";

	public static HttpRequestHttpHeaders parseHttpHeaders(String httpHeader) {
		Map<String, String> headers = new HashMap<>();

		String[] headerLine = httpHeader.split("\n");

		for (int i = FIRST_INDEX_EXCLUDING_START_LINE; i < headerLine.length; i++) {
			String[] headerPair = headerLine[i].split(HEADER_REGEX);
			headers.put(headerPair[0], headerPair[1]);
		}

		return new HttpRequestHttpHeaders(headers);
	}

	public static HttpRequestStartLine parseStartLine(String httpHeader) {
		String startLine = httpHeader.split("\n")[0];
		String[] startLineHeaders = startLine.split(START_LINE_REGEX);

		String httpMethod = startLineHeaders[0];
		String requestTarget = startLineHeaders[1];
		String httpVersion = startLineHeaders[2];

		return new HttpRequestStartLine(httpMethod, requestTarget, httpVersion);
	}
}
