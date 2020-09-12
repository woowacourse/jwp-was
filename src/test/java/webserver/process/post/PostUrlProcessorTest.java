package webserver.process.post;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.HeaderName;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;
import webserver.http.HttpRequest;
import webserver.process.HttpProcessor;

class PostUrlProcessorTest {

	@DisplayName("/user/create 요청 처리")
	@Test
	void apply() {
		HashMap<String, String> headers = new HashMap<>();
		headers.put(HeaderName.Method.name(), "POST");
		headers.put(HeaderName.RequestUrl.name(), "/user/create");
		HttpHeaders httpHeaders = new HttpHeaders(headers);
		HttpBody httpBody = new HttpBody(
			"userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
		HttpRequest httpRequest = new HttpRequest(httpHeaders, httpBody);

		assertThat(HttpProcessor.process(httpRequest)).isEqualTo("create user : userId=javajigi".getBytes());
	}
}