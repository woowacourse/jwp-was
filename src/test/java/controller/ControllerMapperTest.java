package controller;

import http.HttpMethod;
import http.RequestUri;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {
    @DisplayName("요청 정보로부터 ControllerMapper를 가져오는 기능 테스트")
    @Test
    void from() {
        RequestUri requestUri = new RequestUri(
                HttpMethod.GET,
                "/user/create",
                new HashMap<>()
        );

        ControllerMapper controllerMapper = ControllerMapper.from(requestUri).get();

        assertThat(controllerMapper).isEqualTo(ControllerMapper.CREATE_USER);
    }
}
