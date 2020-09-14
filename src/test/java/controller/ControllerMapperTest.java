package controller;

import http.request.HttpMethod;
import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {
    @DisplayName("요청 정보로부터 ControllerMapper를 가져오는 기능 테스트")
    @Test
    void from() {
        RequestLine requestLine = new RequestLine(
                HttpMethod.POST,
                "/user/create"
        );

        ControllerMapper controllerMapper = ControllerMapper.from(requestLine).get();

        assertThat(controllerMapper).isEqualTo(ControllerMapper.CREATE_USER);
    }
}
