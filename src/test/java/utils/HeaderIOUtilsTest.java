package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.HttpMainHeader;

class HeaderIOUtilsTest {

	@DisplayName("request header의 요청 url을 파싱한다.")
	@Test
	void parseMainHeader() {
		String method = "GET";
		String requestUrl = "/user/1";
		String mainHeaders = String.format("%s %s HTTP/1.1", method, requestUrl);

		HttpMainHeader actual = HeaderIOUtils.parseMainHeader(mainHeaders);

		assertThat(actual.getHttpMethod().name()).isEqualTo("GET");
		assertThat(actual.getRequestUrl()).isEqualTo(requestUrl);
	}
}