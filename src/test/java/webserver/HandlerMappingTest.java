package webserver;

import controller.CreateUserController;
import controller.LoginController;
import controller.UserListController;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpStartLine;
import org.junit.jupiter.api.Test;
import webserver.exception.InvalidUriException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerMappingTest {
    @Test
    void 유저_생성_URL_맵핑() {
        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine("/user/create", HttpMethod.POST))
                .build();

        assertThat(HandlerMapping.handle(httpRequest) instanceof CreateUserController).isTrue();
    }

    @Test
    void 로그인_URL_맵핑() {
        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine("/user/login", HttpMethod.POST))
                .build();

        assertThat(HandlerMapping.handle(httpRequest) instanceof LoginController).isTrue();
    }

    @Test
    void 유저_목록_조회_URL_맵핑() {
        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine("/user/list", HttpMethod.POST))
                .build();

        assertThat(HandlerMapping.handle(httpRequest) instanceof UserListController).isTrue();
    }

    @Test
    void 존재하지_않는_URL_맵핑_에러() {
        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine("/user/create/1", HttpMethod.GET))
                .build();

        assertThrows(InvalidUriException.class, () -> HandlerMapping.handle(httpRequest));
    }
}