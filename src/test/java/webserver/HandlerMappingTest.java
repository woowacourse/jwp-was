package webserver;

import controller.CreateUserController;
import http.HttpMethod;
import http.HttpRequest;
import org.junit.jupiter.api.Test;
import webserver.exception.InvalidUriException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerMappingTest {
    @Test
    void 유저_생성_URL_맵핑() {
        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder()
                .uri("/user/create")
                .method(HttpMethod.GET)
                .build();


        assertThat(HandlerMapping.handle(httpRequest) instanceof CreateUserController).isTrue();
    }

    @Test
    void 존재하지_않는_URL_맵핑_에러() {
        HttpRequest httpRequest = new HttpRequest.HttpRequestBuilder()
                .uri("/user/create/1")
                .method(HttpMethod.GET)
                .build();

        assertThrows(InvalidUriException.class, () -> HandlerMapping.handle(httpRequest));
    }
}