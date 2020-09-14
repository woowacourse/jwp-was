package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestStartLine;
import webserver.http.request.header.HttpRequestHttpHeaders;

class BodyIOUtilsTest {

	@Test
	void parseHttpBodyByGet() throws IOException {
		HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine("GET", "", "");
		HashMap<String, String> headers = new HashMap<>();
		HttpRequestHttpHeaders httpRequestHttpHeaders = new HttpRequestHttpHeaders(headers);
		String body = BodyIOUtils.parseHttpBody(httpRequestStartLine, httpRequestHttpHeaders,
			new BufferedReader(new InputStreamReader(System.in)));

		assertThat(body).isEqualTo(HttpRequestBody.EMPTY_BODY.getHttpBody());
	}
}