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

    @DisplayName("controller에 명시되어있는 요청인지 확인 테스트 - true")
    @Test
    void isApiWhenTrue() {
        ControllerMapper.getInstance().addController(new UserController());

        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, new UserController().getPath()),
                new RequestHeader(new HashMap<>()),
                new RequestParams(new HashMap<>())
        );

        assertThat(ControllerMapper.getInstance().isApi(httpRequest)).isTrue();
    }

    @DisplayName("controller에 명시되어있는 요청인지 확인 테스트 - false")
    @Test
    void isApiWhenFalse() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, "/abcd"),
                new RequestHeader(new HashMap<>()),
                new RequestParams(new HashMap<>())
        );
        assertThat(ControllerMapper.getInstance().isApi(httpRequest)).isFalse();
    }

    @DisplayName("httpRequest로부터 일치하는 controller를 찾는 기능 테스트")
    @Test
    void map() {
        ControllerMapper.getInstance().addController(new UserController());

        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(HttpMethod.POST, new UserController().getPath()),
                new RequestHeader(new HashMap<>()),
                new RequestParams(new HashMap<>())
        );
        Controller controller = ControllerMapper.getInstance().map(httpRequest);

        assertThat(controller).isInstanceOf(UserController.class);
    }
}
