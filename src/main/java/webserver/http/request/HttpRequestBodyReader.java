package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import webserver.utils.IOUtils;
import webserver.utils.QueryStringSeparator;

public class HttpRequestBodyReader {
	public static Map<String, String> readRequestBody(BufferedReader bufferedReader, String length) throws IOException {
		String body = IOUtils.readData(bufferedReader, Integer.parseInt(length));
		return QueryStringSeparator.separate(body);
	}
}
