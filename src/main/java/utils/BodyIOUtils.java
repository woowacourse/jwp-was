package utils;

import static java.lang.Integer.*;
import static webserver.http.HttpBody.*;

import java.io.BufferedReader;
import java.io.IOException;

import webserver.http.HttpHeaders;

public class BodyIOUtils {

	public static String parseHttpBody(HttpHeaders httpHeaders, BufferedReader bufferedReader) throws IOException {
		if (httpHeaders.hasNotBody()) {
			return EMPTY_BODY.getHttpBody();
		}

		String contentLength = httpHeaders.getByHeaderName("Content-Length");
		return IOUtils.readData(bufferedReader, parseInt(contentLength));
	}
}
