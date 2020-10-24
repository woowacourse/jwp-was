package web.request;

import exception.InvalidRequestBodyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class RequestBodyTest {
    @Test
    @DisplayName("RequestBody 객체가 올바르게 생성된다")
    void createRequestBodyTest() {
        String parameters = "poo=bar&test=success";

        RequestBody requestBody = new RequestBody(parameters);

        Assertions.assertThat(requestBody.getParameterByKey("poo")).isEqualTo("bar");
        Assertions.assertThat(requestBody.getParameterByKey("test")).isEqualTo("success");

    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("예외 테스트: RequestBody 생성 중 잘못된 값이 전달되면, 예외를 발생시킨다.")
    void createHttpRequestExceptionTest(String invalidValue) {
        Assertions.assertThatThrownBy(() -> new RequestBody(invalidValue))
                .isInstanceOf(InvalidRequestBodyException.class)
                .hasMessage("Request Body의 값이 올바르지 않습니다");
    }
}
