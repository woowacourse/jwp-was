package http.request;

import java.io.BufferedReader;
import java.io.IOException;

import http.method.HttpMethod;

public class HttpRequestReader {
	public static HttpRequest readHttpRequest(BufferedReader bufferedReader) throws IOException {
		HttpRequestHeader httpRequestHeader = new HttpRequestHeader(HttpRequestHeaderReader.readRequest(bufferedReader));

		if (HttpMethod.POST.isSameMethod(httpRequestHeader.getRequestElement("Method"))) {
			HttpRequestBody httpRequestBody = new HttpRequestBody(HttpRequestBodyReader.readRequestBody(bufferedReader, httpRequestHeader.getRequestElement("Content-Length")));
			return new HttpRequest(httpRequestHeader, httpRequestBody);
		}
		return new HttpRequest(httpRequestHeader);
	}
}
