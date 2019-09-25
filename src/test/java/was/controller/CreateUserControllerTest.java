package was.controller;

import db.DataBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import was.exception.MethodNotAllowedException;
import was.model.User;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateUserControllerTest {

    @ParameterizedTest
    @MethodSource("createHttpPostRequest")
    void 사용자_생성(InputStream inputStream) throws Exception {
        Controller controller = new CreateUserController();
        HttpRequest httpRequest = new HttpRequest();
        HttpResponse httpResponse = new HttpResponse();

        HttpRequestParser.parse(inputStream, httpRequest);

        controller.service(httpRequest, httpResponse);

        assertEquals(new User("javajigi", "1234", "javajigi", "javajigi@slipp.net"),
                DataBase.findUserById("javajigi"));
    }

    static Stream<Arguments> createHttpPostRequest() {
        String str =
                "POST /user/create HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 59\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Accept: */*\n" +
                        "\n" +
                        "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n\r";

        return Stream.of(
                Arguments.of(new ByteArrayInputStream(
                        str.getBytes()
                ))
        );
    }

    @ParameterizedTest
    @MethodSource("createHttpGetRequest")
    void 지원하지_않는_메서드_요청(InputStream inputStream) throws Exception {
        Controller controller = new CreateUserController();
        HttpRequest httpRequest = new HttpRequest();
        HttpResponse httpResponse = new HttpResponse();

        HttpRequestParser.parse(inputStream, httpRequest);

        assertThrows(MethodNotAllowedException.class, () -> {
            controller.service(httpRequest, httpResponse);
        });
    }

    static Stream<Arguments> createHttpGetRequest() {
        String str =
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*\n\r";
        return Stream.of(
                Arguments.of(new ByteArrayInputStream(
                        str.getBytes()
                ))
        );
    }
}