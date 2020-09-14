package utils;

import java.util.HashMap;
import java.util.Map;

import webserver.http.request.HttpRequestHeaderName;
import webserver.http.request.HttpRequestHttpHeaders;

public class HeaderIOUtils {

	public static final int FIRST_INDEX_EXCLUDING_MAIN_HEADER = 1;

	public static HttpRequestHttpHeaders parseHttpHeaders(String httpHeader) {
		Map<String, String> headers = new HashMap<>();

		String[] headerLine = httpHeader.split("\n");

		parseMainHeaders(headers, headerLine[0]);

		parseExceptMainHeaders(headers, headerLine);

		return new HttpRequestHttpHeaders(headers);
	}

	private static void parseExceptMainHeaders(Map<String, String> headers, String[] headerLine) {
		for (int i = FIRST_INDEX_EXCLUDING_MAIN_HEADER; i < headerLine.length; i++) {
			String[] headerPair = headerLine[i].split(": ");
			headers.put(headerPair[0], headerPair[1]);
		}
	}

	private static void parseMainHeaders(Map<String, String> headers, String s) {
		String[] mainHeaders = s.split(" ");
		headers.put(HttpRequestHeaderName.Method.name(), mainHeaders[0]);
		headers.put(HttpRequestHeaderName.RequestUrl.name(), mainHeaders[1]);
		headers.put("httpVersion", mainHeaders[2]);
	}
}
