package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import http.HeaderElement;
import http.method.HttpMethod;

public class HttpRequestReader {
	public static HttpRequest readHttpRequest(BufferedReader bufferedReader) throws IOException {
		HttpRequestHeader httpRequestHeader = new HttpRequestHeader(HttpRequestHeaderReader.readRequest(bufferedReader));

		if (HttpMethod.POST.isSameMethod(httpRequestHeader.getRequestElement(HeaderElement.METHOD))) {
			HttpRequestBody httpRequestBody = new HttpRequestBody(HttpRequestBodyReader.readRequestBody(bufferedReader, httpRequestHeader.getRequestElement(HeaderElement.CONTENT_LENGTH)));
			return new HttpRequest(httpRequestHeader, httpRequestBody);
		}
		return new HttpRequest(httpRequestHeader);
	}
}
