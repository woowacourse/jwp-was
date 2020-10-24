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

public class RequestHeadersTest {
	private static final String FILE_DIRECTORY = "./src/test/resources/";
	private static final String POST_REQUEST = "post_request.txt";
	private static final String GET_REQUEST = "get_request.txt";

	private RequestHeaders requestHeaders;

	@DisplayName("GET 요청에 대한 Of 메서드를 테스트한다.")
	@Test
	void getOfTest() throws IOException {
		BufferedReader br = createBufferedReader(GET_REQUEST);
		br.readLine();
		requestHeaders = RequestHeaders.of(br);

		assertAll(
			() -> assertThat(requestHeaders.getHeader("Host")).isEqualTo("localhost:8080"),
			() -> assertThat(requestHeaders.getHeader("Connection")).isEqualTo("keep-alive"),
			() -> assertThat(requestHeaders.getHeader("Accept")).isEqualTo("*/*")
		);
	}

	@DisplayName("POST 요청에 대한 Of 메서드를 테스트한다.")
	@Test
	void postOfTest() throws IOException {
		BufferedReader br = createBufferedReader(POST_REQUEST);
		br.readLine();
		requestHeaders = RequestHeaders.of(br);

		assertAll(
			() -> assertThat(requestHeaders.getHeader("Connection")).isEqualTo("keep-alive"),
			() -> assertThat(requestHeaders.getHeader("Accept")).isEqualTo("*/*"),
			() -> assertThat(requestHeaders.getHeader("Content-Length")).isEqualTo("46"),
			() -> assertThat(requestHeaders.getHeader("Content-Type")).isEqualTo("application/x-www-form-urlencoded")
		);
	}

	@DisplayName("POST 요청시 header에 담긴 Content-Length 내용을 확인한다.")
	@Test
	void getContentLengthTest() throws IOException {
		BufferedReader br = createBufferedReader(POST_REQUEST);
		br.readLine();
		requestHeaders = RequestHeaders.of(br);

		assertThat(requestHeaders.getContentSize()).isEqualTo(46);
	}

	private BufferedReader createBufferedReader(String fileName) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(new File(FILE_DIRECTORY + fileName));
		return new BufferedReader(new InputStreamReader(inputStream));
	}
}
