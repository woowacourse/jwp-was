package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestBodyTest {

    @DisplayName("Request Body를 입력받았을 때 데이터가 RequestBody 클래스의 필드에 잘 담기")
    @Test
    void createRequestBody() throws UnsupportedEncodingException {
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        RequestBody requestBody = new RequestBody(body);
        assertAll(
            () -> assertThat(requestBody.getParams().get("userId")).isEqualTo("javajigi"),
            () -> assertThat(requestBody.getParams().get("password")).isEqualTo("password"),
            () -> assertThat(requestBody.getParams().get("name")).isEqualTo("박재성"),
            () -> assertThat(requestBody.getParams().get("email")).isEqualTo("javajigi@slipp.net")
        );
    }
}
