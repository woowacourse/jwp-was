package http.request.factory;

import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestStartLine;

import java.util.List;

public class HttpRequestFactory {
	private static final String EMPTY = "";
	private static final int START_LINE_INDEX = 0;
	private static final int HEADER_START_INDEX = 1;

	public static HttpRequest create(List<String> lines) {
		int emptyLineIndex = lines.indexOf(EMPTY);

		if (emptyLineIndex == -1) {
			emptyLineIndex = lines.size();
		}

		HttpRequestStartLine httpRequestStartLine = HttpRequestStartLineFactory.create(lines.get(START_LINE_INDEX));
		HttpRequestHeader httpRequestHeader = HttpRequestHeaderFactory.create(lines.subList(HEADER_START_INDEX, emptyLineIndex));
		HttpRequestBody httpRequestBody = HttpRequestBodyFactory.create(EMPTY);

		if (httpRequestStartLine.hasBody()) {
			httpRequestBody = HttpRequestBodyFactory.create(lines.get(lines.size() - 1));
		}
		return new HttpRequest(httpRequestStartLine, httpRequestHeader, httpRequestBody);
	}
}
