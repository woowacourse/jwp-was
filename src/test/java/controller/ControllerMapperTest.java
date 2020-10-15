package controller;

import static org.assertj.core.api.Assertions.assertThat;

import application.controller.UserController;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import request.HttpRequest;

class ControllerMapperTest {

    @ParameterizedTest
    @MethodSource("httpMethods")
    @DisplayName("UserController 반환")
    void findUserController(String httpRequestFormat) {
        ControllerMapper controllerMapper = new ControllerMapper();

        HttpRequest httpRequest = new HttpRequest(httpRequestFormat, "");

        assertThat(controllerMapper.findController(httpRequest))
            .isInstanceOf(UserController.class);
    }

    private static Stream<Arguments> httpMethods() {
        return Stream.of(
            // user/create
            Arguments.of("GET /user/create?id=1 HTTP/1.1\n"),
            Arguments.of("POST /user/create?id=1 HTTP/1.1\nHost: localhost:8080\n"),
            Arguments.of("PUT /user/create?id=1 HTTP/1.1\n"),
            Arguments.of("DELETE /user/create?id=1 HTTP/1.1\nHost: localhost:8080\n"),
            // user/login
            Arguments.of("GET /user/login?id=1 HTTP/1.1\n"),
            Arguments.of("POST /user/login?id=1 HTTP/1.1\nHost: localhost:8080\n"),
            Arguments.of("PUT /user/login?id=1 HTTP/1.1\n"),
            Arguments.of("DELETE /user/login?id=1 HTTP/1.1\nHost: localhost:8080\n")
        );
    }

    @Test
    @DisplayName("정의된 uri 가 아닌 경우 StaticFileController 반환")
    void findStaticFileController() {
        ControllerMapper controllerMapper = new ControllerMapper();

        String httpRequestFormat = "GET /user HTTP/1.1\n"
            + "Host: localhost:8080\n";
        HttpRequest httpRequest = new HttpRequest(httpRequestFormat, "");

        assertThat(controllerMapper.findController(httpRequest))
            .isInstanceOf(StaticFileController.class);
    }
}
