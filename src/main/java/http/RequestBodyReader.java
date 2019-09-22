package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestBodyReader {
	public static Map<String, String> readRequestBody(BufferedReader bufferedReader, String length) throws IOException {
		String body = IOUtils.readData(bufferedReader, Integer.parseInt(length));
		return QueryStringSeparator.separate(body);
	}
}
