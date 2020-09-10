package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeaderIOUtilsTest {

	@DisplayName("request header의 요청 url을 파싱한다.")
	@Test
	void parseUrl() {
		String requestUrl = "/user/1";
		String mainHeaders = String.format("GET %s HTTP/1.1", requestUrl);
		String actual = HeaderIOUtils.parseUrl(mainHeaders);

		assertThat(actual).isEqualTo(requestUrl);
	}
}