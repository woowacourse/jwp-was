package utils;

import static java.lang.Integer.*;
import static webserver.http.request.HttpRequestBody.*;

import java.io.BufferedReader;
import java.io.IOException;

import webserver.http.request.HttpRequestHttpHeaders;
import webserver.http.response.HttpResponseHeaderName;

public class BodyIOUtils {

	public static String parseHttpBody(HttpRequestHttpHeaders httpRequestHttpHeaders,
		BufferedReader bufferedReader) throws IOException {
		if (httpRequestHttpHeaders.hasNotBody()) {
			return EMPTY_BODY.getHttpBody();
		}

		String contentLength = httpRequestHttpHeaders.getByHeaderName(HttpResponseHeaderName.CONTENT_LENGTH.name());
		return IOUtils.readData(bufferedReader, parseInt(contentLength));
	}
}
