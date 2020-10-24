package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpRequestTest {
	private static final String FILE_DIRECTORY = "./src/test/resources/";
	private static final String POST_REQUEST = "post_request.txt";
	private static final String GET_REQUEST = "get_request.txt";
	private static final String ANOTHER_POST_REQUEST = "new_post_request.txt";

	private HttpRequest httpRequest;

	@DisplayName("GET 요청에 대한 HttpRequest를 생성한다.")
	@Test
	void createGetHttpRequestTest() throws IOException {
		httpRequest = createHttpRequest(GET_REQUEST);

		assertAll(
			() -> assertThat(httpRequest.getMethod()).isEqualTo("GET"),
			() -> assertThat(httpRequest.getUrl()).isEqualTo("/user/create"),
			() -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
			() -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
			() -> assertThat(httpRequest.getParameter("password")).isEqualTo("password"),
			() -> assertThat(httpRequest.getParameter("name")).isEqualTo("JaeSung")
		);
	}

	@DisplayName("POST 요청에 대한 HttpRequest를 생성한다.")
	@Test
	void createPostHttpRequestTest() throws IOException {
		httpRequest = createHttpRequest(POST_REQUEST);

		assertAll(
			() -> assertThat(httpRequest.getMethod()).isEqualTo("POST"),
			() -> assertThat(httpRequest.getUrl()).isEqualTo("/user/create"),
			() -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
			() -> assertThat(httpRequest.getBody()).isEqualTo("userId=javajigi&password=password&name=JaeSung")
		);
	}

	@DisplayName("또 다른 POST 요청에 대한 HttpRequest를 생성한다.")
	@Test
	void createAnotherPostHttpRequestTest() throws IOException {
		httpRequest = createHttpRequest(ANOTHER_POST_REQUEST);

		assertAll(
			() -> assertThat(httpRequest.getMethod()).isEqualTo("POST"),
			() -> assertThat(httpRequest.getUrl()).isEqualTo("/user/create"),
			() -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
			() -> assertThat(httpRequest.getParameter("id")).isEqualTo("1"),
			() -> assertThat(httpRequest.getBody()).isEqualTo("userId=javajigi&password=password&name=JaeSung")
		);
	}

	private HttpRequest createHttpRequest(String fileName) throws IOException {
		InputStream inputStream = new FileInputStream(new File(FILE_DIRECTORY + fileName));

		return new HttpRequest(inputStream);
	}
}
