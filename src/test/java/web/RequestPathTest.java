package web;

import exception.InvalidRequestPathException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.RequestPath;

public class RequestPathTest {
    private static final Logger logger = LoggerFactory.getLogger(RequestPathTest.class);

    @Test
    @DisplayName("RequestPath 객체가 올바르게 생성된다")
    void createRequestPathTest() {
        String path = "/index.html";

        RequestPath requestPath = new RequestPath(path);

        Assertions.assertThat(requestPath.getTarget()).isEqualTo(path);
        Assertions.assertThat(requestPath.getPathParameters()).isEmpty();
    }

    @Test
    @DisplayName("RequestPath 객체에 파라미터가 포함된 경로를 보내면, 올바르게 생성된다.")
    void createRequestPathWithParamsTest() {
        String path = "/index.html?poo=bar&test=success";

        RequestPath requestPath = new RequestPath(path);

        Assertions.assertThat(requestPath.getTarget()).isEqualTo("/index.html");

        Assertions.assertThat(requestPath.getPathParameters().get("poo")).isEqualTo("bar");
        Assertions.assertThat(requestPath.getPathParameters().get("test")).isEqualTo("success");
    }

    @Test
    @DisplayName("예외 테스트: RequestPath 생성 중 잘못된 값이 전달되면, 예외를 발생시킨다.")
    void createHttpRequestExceptionTest() {
        String invalidRequest = "/index.html?=123";

        Assertions.assertThatThrownBy(() -> new RequestPath(invalidRequest))
                .isInstanceOf(InvalidRequestPathException.class)
                .hasMessage("잘못된 경로 요청입니다");
    }
}
