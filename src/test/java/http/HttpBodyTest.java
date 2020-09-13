package http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpBodyTest {

    @DisplayName("올바른 입력에 대해 HttpBody를 생성한다.")
    @Test
    void createHttpBody() {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("userId", "javajigi");
        body.put("password", "password");
        body.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        body.put("email", "javajigi%40slipp.net");

        HttpBody httpBody = new HttpBody(body);

        assertAll(
            () -> assertThat("javajigi").isEqualTo(httpBody.getValue("userId")),
            () -> assertThat("password").isEqualTo(httpBody.getValue("password")),
            () -> assertThat("%EB%B0%95%EC%9E%AC%EC%84%B1").isEqualTo(httpBody.getValue("name")),
            () -> assertThat("javajigi%40slipp.net").isEqualTo(httpBody.getValue("email"))
        );
    }
}