package webserver.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.domain.request.RequestParams;

public class RequestParamsTest {

	@DisplayName("URI의 query에 빈 값이 들어갔을 경우 빈값이 잘 들어가는지 확인 한다.")
	@Test
	void emptyValue() throws UnsupportedEncodingException {
		RequestParams requestParams = new RequestParams("name=&value=");
		Map<String, String> params = requestParams.getParams();
		assertAll(
			() -> assertThat(params.get("name")).isEqualTo(""),
			() -> assertThat(params.get("value")).isEqualTo("")
		);
	}

	@DisplayName("URI의 query에 중복된 키값이 들어가는 경우 마지막값이 저장되게 한다.")
	@Test
	void duplicateKeys() throws UnsupportedEncodingException {
		RequestParams requestParams = new RequestParams("name=a&name=b");
		Map<String, String> params = requestParams.getParams();
		assertThat(params.get("name")).isEqualTo("b");
	}
}
