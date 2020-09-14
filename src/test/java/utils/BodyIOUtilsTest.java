package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestHeaderName;
import webserver.http.request.HttpRequestHttpHeaders;

class BodyIOUtilsTest {

	@Test
	void parseHttpBodyByGet() throws IOException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HttpRequestHeaderName.Method.name(), "GET");
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		String body = BodyIOUtils.parseHttpBody(httpRequestHttpHeaders,
			new BufferedReader(new InputStreamReader(System.in)));

		assertThat(body).isEqualTo(HttpRequestBody.EMPTY_BODY.getHttpBody());
	}
}