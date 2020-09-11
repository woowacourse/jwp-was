package webserver.domain.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {

	private final String testDirectory = "./src/test/java/resources/";

	@DisplayName("HttpRequest 받았을 때 RequestHeader URI의 QueryParam이 저장되는지 확인한다.")
	@Test
	void checkQueryParam() throws IOException {
		InputStream in = new FileInputStream(new File(testDirectory + "http_get.txt"));
		HttpRequest httpRequest = new HttpRequest(
			new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
		assertAll(
			() -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
			() -> assertThat(httpRequest.getParameter("password")).isEqualTo("password"),
			() -> assertThat(httpRequest.getParameter("name")).isEqualTo("박재성"),
			() -> assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net")
		);
	}

	@DisplayName("HttpRequest 받았을 때 RequestBody의 FormData가 저장되는지 확인한다.")
	@Test
	void checkFormData() throws IOException {
		InputStream in = new FileInputStream(new File(testDirectory + "http_post.txt"));
		HttpRequest httpRequest = new HttpRequest(
			new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
		assertAll(
			() -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
			() -> assertThat(httpRequest.getParameter("password")).isEqualTo("password"),
			() -> assertThat(httpRequest.getParameter("name")).isEqualTo("박재성"),
			() -> assertThat(httpRequest.getParameter("email")).isEqualTo("javajigi@slipp.net")
		);
	}

	@DisplayName("HttpRequest 받았을 때 RequestLine가 저장되는지 확인한다.")
	@Test
	void checkRequestLine() throws IOException {
		InputStream in = new FileInputStream(new File(testDirectory + "http_post.txt"));
		HttpRequest httpRequest = new HttpRequest(
			new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
		assertAll(
			() -> assertThat(httpRequest.getRequestMethod()).isEqualTo(RequestMethod.POST),
			() -> assertThat(httpRequest.getPath()).isEqualTo("/user/create")
		);
	}

	@DisplayName("HttpRequest를 받았을 때 RequestHeader가 저장되는지 확인한다.")
	@Test
	void checkRequestHeader() throws IOException {
		InputStream in = new FileInputStream(new File(testDirectory + "http_post.txt"));
		HttpRequest httpRequest = new HttpRequest(
			new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));

		assertAll(
			() -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
			() -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
			() -> assertThat(httpRequest.getHeader("Content-Length")).isEqualTo("93"),
			() -> assertThat(httpRequest.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded"),
			() -> assertThat(httpRequest.getHeader("Accept")).isEqualTo("*/*")
		);
	}
}