package utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.HttpHeaders;
import webserver.process.HttpProcessor;

class HeaderIOUtilsTest {

	@DisplayName("request header의 요청 url을 파싱한다.")
	@Test
	void parseMainHeader() {
		String httpHeader = "GET /index.html HTTP/1.1\n"
			+ "Host: localhost:8080\n"
			+ "Connection: keep-alive\n"
			+ "Cache-Control: max-age=0\n"
			+ "Upgrade-Insecure-Requests: 1\n"
			+ "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36\n"
			+ "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n"
			+ "Sec-Fetch-Site: none\n"
			+ "Sec-Fetch-Mode: navigate\n"
			+ "Sec-Fetch-User: ?1\n"
			+ "Sec-Fetch-Dest: document\n"
			+ "Accept-Encoding: gzip, deflate, br\n"
			+ "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7";

		HttpHeaders actual = HeaderIOUtils.parseHttpHeaders(httpHeader);

		assertAll(
			() -> assertThat(actual.size()).isEqualTo(15),
			() -> assertThat(actual.getHttpMethod()).isEqualTo(HttpProcessor.GET.name()),
			() -> assertThat(actual.getUrl()).isEqualTo("/index.html")
		);
	}
}