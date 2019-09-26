package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import http.Header;
import http.method.HttpMethod;

public class HttpRequestReader {
	public static HttpRequest readHttpRequest(BufferedReader bufferedReader) throws IOException {
		HttpRequestHeader httpRequestHeader = new HttpRequestHeader(HttpRequestHeaderReader.readRequest(bufferedReader));

		if (HttpMethod.POST.isSameMethod(httpRequestHeader.getRequestElement(Header.METHOD))) {
			HttpRequestBody httpRequestBody = new HttpRequestBody(HttpRequestBodyReader.readRequestBody(bufferedReader, httpRequestHeader.getRequestElement(Header.CONTENT_LENGTH)));
			return new HttpRequest(httpRequestHeader, httpRequestBody);
		}
		return new HttpRequest(httpRequestHeader);
	}
}
