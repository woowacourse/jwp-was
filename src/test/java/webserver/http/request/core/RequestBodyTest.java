package webserver.http.request.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.POST_BODY;

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
}