package webserver.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestBodyTest {

    @DisplayName("Request Body를 입력받았을 때 데이터가 RequestBody 클래스의 필드에 잘 담기는지 확인한다.")
    @Test
    void createRequestBody() throws UnsupportedEncodingException {
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        HttpRequestBody httpRequestBody = new HttpRequestBody(body);
        assertAll(
            () -> assertThat(httpRequestBody.getParams().get("userId")).isEqualTo("javajigi"),
            () -> assertThat(httpRequestBody.getParams().get("password")).isEqualTo("password"),
            () -> assertThat(httpRequestBody.getParams().get("name")).isEqualTo("박재성"),
            () -> assertThat(httpRequestBody.getParams().get("email")).isEqualTo("javajigi@slipp.net")
        );
    }
}
