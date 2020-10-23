package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {
	private static final String FILE_DIRECTORY = "./src/test/resources/";
	private static final String POST_REQUEST = "post_request.txt";
	private static final String GET_REQUEST = "get_request.txt";
	private static final String ANOTHER_POST_REQUEST = "new_post_request.txt";

	@DisplayName("GET 요청에 대한 RequestLine을 생성한다.")
	@Test
	void createRequestLineWhenGetRequestTest() throws IOException {
		BufferedReader br = createBufferedReader(GET_REQUEST);
		RequestLine requestLine = RequestLine.of(br);

		assertAll(
			() -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
			() -> assertThat(requestLine.getUrl()).isEqualTo("/user/create"),
			() -> assertThat(requestLine.getParameter("userId")).isEqualTo("javajigi"),
			() -> assertThat(requestLine.getParameter("password")).isEqualTo("password"),
			() -> assertThat(requestLine.getParameter("name")).isEqualTo("JaeSung")
		);
	}

	@DisplayName("POST 요청에 대한 RequestLine을 생성한다.")
	@Test
	void createRequestLineWhenPostRequestTest() throws IOException {
		BufferedReader br = createBufferedReader(POST_REQUEST);
		RequestLine requestLine = RequestLine.of(br);

		assertAll(
			() -> assertThat(requestLine.getMethod()).isEqualTo("POST"),
			() -> assertThat(requestLine.getUrl()).isEqualTo("/user/create")
		);
	}

	@DisplayName("또 다른 POST 요청에 대한 RequestLine을 생성한다.")
	@Test
	void createRequestLineWhenAnotherPostRequestTest() throws IOException {
		BufferedReader br = createBufferedReader(ANOTHER_POST_REQUEST);
		RequestLine requestLine = RequestLine.of(br);

		assertAll(
			() -> assertThat(requestLine.getMethod()).isEqualTo("POST"),
			() -> assertThat(requestLine.getUrl()).isEqualTo("/user/create")
		);
	}

	private BufferedReader createBufferedReader(String fileName) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(new File(FILE_DIRECTORY + fileName));
		return new BufferedReader(new InputStreamReader(inputStream));
	}
}
