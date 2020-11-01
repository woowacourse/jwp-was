package fixtures;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import webserver.http.request.HttpRequest;

public class HttpRequestGenerator {
	public static HttpRequest createPostHttpRequest(String fileName) throws IOException {
		BufferedInputStream inputStream = new BufferedInputStream(
			new FileInputStream(new File("./src/test/resources/" + fileName)));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		return new HttpRequest(bufferedReader);
	}
}
