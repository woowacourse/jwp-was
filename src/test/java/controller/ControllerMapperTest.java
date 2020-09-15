package controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestHeader;
import http.request.RequestLine;
import http.request.RequestParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {

    @DisplayName("controller에 명시되어있는 요청인지 확인 테스트")
    @Test
    void isApi() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, ControllerMapper.USER_CONTROLLER_PATH),
                new RequestHeader(new HashMap<>()),
                new RequestParams(new HashMap<>())
        );
        assertThat(ControllerMapper.isApi(httpRequest)).isTrue();
    }

    @DisplayName("httpRequest로부터 일치하는 controller를 찾는 기능 테스트")
    @Test
    void map() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, ControllerMapper.USER_CONTROLLER_PATH),
                new RequestHeader(new HashMap<>()),
                new RequestParams(new HashMap<>())
        );
        Controller controller = ControllerMapper.map(httpRequest);
        assertThat(controller).isInstanceOf(UserController.class);
    }
}
