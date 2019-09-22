package utils;

import java.io.BufferedReader;
import java.io.IOException;

import method.HttpMethod;
import model.RequestHeader;
import webserver.HttpRequest;
import webserver.RequestBody;

public class HttpRequestReader {

	public static HttpRequest readHttpRequest(BufferedReader bufferedReader) throws IOException {
		RequestHeader requestHeader = new RequestHeader(RequestHeaderReader.readRequest(bufferedReader));

		if (HttpMethod.POST.isSameMethod(requestHeader.getRequestElement("Method"))) {
			RequestBody requestBody = new RequestBody(RequestBodyReader.readRequestBody(bufferedReader, requestHeader.getRequestElement("Content-Length")));
			return new HttpRequest(requestHeader, requestBody);
		}
		return new HttpRequest(requestHeader);
	}
}
