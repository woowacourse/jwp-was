package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBodyReader {
	public static String readRequestBody(BufferedReader bufferedReader, String length) throws IOException {
		return IOUtils.readData(bufferedReader, Integer.parseInt(length));
	}
}
