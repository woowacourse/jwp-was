package webserver.http.request.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.exception.CanNotParseDataException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.UtilData.*;

class RequestBodyTest {
    @Test
    @DisplayName("Post 로 요청 할 경우 데이터를 추출 한다.")
    void RequestPostHasParam() {
        RequestBody requestBody = new RequestBody(POST_BODY);
        assertThat(requestBody.getValue("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getValue("password")).isEqualTo("password");
        assertThat(requestBody.getValue("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestBody.getValue("email")).isEqualTo("javajigi%40slipp.net");
    }

    @Test
    @DisplayName("Post 로 요청 할 때 데이터를 추출 할 경우 예외처리를 한다.")
    void RequestPostCanNotExtractParam() {
        assertThrows(CanNotParseDataException.class, () -> new RequestBody(WRONG_POST_BODY_1));
        assertThrows(CanNotParseDataException.class, () -> new RequestBody(WRONG_POST_BODY_2));
    }
}