package web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HttpRequestTest {

	@Test
	void of() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("./src/test/java/web/example.txt"));

		Map<String, String> header = new HashMap<>();
		header.put("Host", "localhost:8080");
		header.put("Connection", "keep-alive");
		header.put("Content-Length", "59");
		header.put("Content-Type", "application/x-www-form-urlencoded");
		header.put("Accept", "*/*");

		Map<String, String> parameter = new HashMap<>();
		parameter.put("userId", "javajigi");
		parameter.put("password", "password");
		parameter.put("name", "박재성");
		parameter.put("email", "javajigi@slipp.net");

		HttpRequest expect = new HttpRequest(HttpMethod.POST, "/user/create", header, parameter);

		HttpRequest request = HttpRequest.of(br);
		assertAll(
			() -> assertThat(request.getMethod()).isEqualTo(expect.getMethod()),
			() -> assertThat(request.getPath()).isEqualTo(expect.getPath()),
			() -> assertThat(request.getHeader("Content-Length")).isEqualTo(expect.getHeader("Content-Length")),
			() -> assertThat(request.getParameter("userId")).isEqualTo(expect.getParameter("userId"))
		);
	}
}