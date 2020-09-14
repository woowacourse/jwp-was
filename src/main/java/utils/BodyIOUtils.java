package utils;

import static java.lang.Integer.*;
import static webserver.http.request.HttpRequestBody.*;

import java.io.BufferedReader;
import java.io.IOException;

import webserver.http.request.HttpRequestStartLine;
import webserver.http.request.header.HttpRequestHeaderName;
import webserver.http.request.header.HttpRequestHttpHeaders;

public class BodyIOUtils {

	public static String parseHttpBody(HttpRequestStartLine httpRequestStartLine,
		HttpRequestHttpHeaders httpRequestHttpHeaders,
		BufferedReader bufferedReader) throws IOException {
		if (httpRequestStartLine.hasNotBody()) {
			return EMPTY_BODY.getHttpBody();
		}

		String contentLength = httpRequestHttpHeaders.getByHeaderName(HttpRequestHeaderName.CONTENT_LENGTH.getValue());

		System.out.println("conetnelength" + contentLength);
		return IOUtils.readData(bufferedReader, parseInt(contentLength));
	}
}
