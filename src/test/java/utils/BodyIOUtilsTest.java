package utils;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import webserver.http.HeaderName;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;

class BodyIOUtilsTest {

	@Test
	void parseHttpBodyByGet() throws IOException {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "GET");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		String body = BodyIOUtils.parseHttpBody(httpHeaders, new BufferedReader(new InputStreamReader(System.in)));

		assertThat(body).isEqualTo(HttpBody.EMPTY_BODY.getHttpBody());
	}
}