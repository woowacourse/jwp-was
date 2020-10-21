package web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.request.RequestPath;

class RequestPathTest {

    @DisplayName("RequestPath 분할 테스트")
    @Test
    void name() {
        String path = "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestPath requestPath = new RequestPath(path);

        assertThat(requestPath.getRequestPath()).isEqualTo("/create");
        assertThat(requestPath.getRequestParameter("userId")).isEqualTo("javajigi");
        assertThat(requestPath.getRequestParameter("password")).isEqualTo("password");
        assertThat(requestPath.getRequestParameter("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestPath.getRequestParameter("email")).isEqualTo("javajigi%40slipp.net");
    }
}