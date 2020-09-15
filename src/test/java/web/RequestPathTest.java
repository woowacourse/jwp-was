package web;

import exception.InvalidRequestParamsException;
import exception.InvalidRequestPathException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import web.request.RequestPath;

public class RequestPathTest {
    @Test
    @DisplayName("RequestPath 객체가 올바르게 생성된다")
    void createRequestPathTest() {
        String path = "/index.html";

        RequestPath requestPath = new RequestPath(path);

        Assertions.assertThat(requestPath.getTarget()).isEqualTo(path);
        Assertions.assertThat(requestPath.getParameters()).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("예외 테스트: RequestPath 생성 중 잘못된 경로가 전달되면, 예외를 발생시킨다.")
    void createHttpRequestExceptionTest(String invalidRequest) {
        Assertions.assertThatThrownBy(() -> new RequestPath(invalidRequest))
                .isInstanceOf(InvalidRequestPathException.class)
                .hasMessage("Request Path의 값이 올바르지 않습니다");
    }

    @Test
    @DisplayName("RequestPath 객체에 파라미터가 포함된 경로를 보내면, 올바르게 생성된다.")
    void createRequestPathWithParamsTest() {
        String path = "/index.html?poo=bar&test=success";

        RequestPath requestPath = new RequestPath(path);

        Assertions.assertThat(requestPath.getTarget()).isEqualTo("/index.html");

        Assertions.assertThat(requestPath.getParameterByKey("poo")).isEqualTo("bar");
        Assertions.assertThat(requestPath.getParameterByKey("test")).isEqualTo("success");
    }

    @Test
    @DisplayName("예외 테스트: RequestPath 생성 중 잘못된 파라미터가 전달되면, 예외를 발생시킨다.")
    void createHttpRequestWithParmasExceptionTest() {
        String invalidRequest = "/index.html?=123";

        Assertions.assertThatThrownBy(() -> new RequestPath(invalidRequest))
                .isInstanceOf(InvalidRequestParamsException.class)
                .hasMessage("Request에 포함된 인자의 값이 올바르지 않습니다");
    }
}
