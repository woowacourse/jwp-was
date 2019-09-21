package webserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestHeaderReader;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderReaderTest {
	@DisplayName("RequestHeader 읽기")
	@Test
	void printRequestHeader() throws IOException {
		String fileName = "src/test/resources/requestExample.txt";
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		List<String> actualRequests = Arrays.asList("GET /index.html HTTP/1.1", "Host: localhost:8080",
				"Connection: keep-alive", "Accept: */*");
		List<String> expectRequests = RequestHeaderReader.printRequest(bufferedReader);
		assertThat(actualRequests).isEqualTo(expectRequests);
	}
}