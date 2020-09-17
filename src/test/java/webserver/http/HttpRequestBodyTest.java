package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.HttpRequestBody;

class HttpRequestBodyTest {

    @DisplayName("올바른 입력에 대해 HttpBody를 생성한다.")
    @Test
    void createHttpBody() {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("userId", "javajigi");
        body.put("password", "password");
        body.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        body.put("email", "javajigi%40slipp.net");

        HttpRequestBody httpRequestBody = new HttpRequestBody(body);

        assertAll(
            () -> assertThat("javajigi").isEqualTo(httpRequestBody.getValue("userId")),
            () -> assertThat("password").isEqualTo(httpRequestBody.getValue("password")),
            () -> assertThat("%EB%B0%95%EC%9E%AC%EC%84%B1").isEqualTo(httpRequestBody.getValue("name")),
            () -> assertThat("javajigi%40slipp.net").isEqualTo(httpRequestBody.getValue("email"))
        );
    }
}