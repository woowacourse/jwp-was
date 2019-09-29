package was;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import was.controller.Controller;
import was.controller.CreateUserController;
import was.controller.MainController;
import webserver.http.exception.NotFoundException;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerMappingTest {

    @ParameterizedTest
    @MethodSource("createValidLocationRequest")
    void 존재하는_경로(InputStream inputStream, Controller controller) {
        HttpRequest httpRequest = new HttpRequest();

        HttpRequestParser.parse(inputStream, httpRequest);

        assertEquals(controller, HandlerMapping.getHandler(httpRequest));
    }

    static Stream<Arguments> createValidLocationRequest() {
        String index = "GET / HTTP/1.1\n\r";
        String userCreate = "GET /user/create HTTP/1.1\n\r";

        return Stream.of(
                Arguments.of(new ByteArrayInputStream(index.getBytes()), MainController.getInstance()),
                Arguments.of(new ByteArrayInputStream(userCreate.getBytes()), CreateUserController.getInstance())
        );
    }

    @ParameterizedTest
    @MethodSource("createInvalidLocationRequest")
    void 존재하지_않는_경로(InputStream inputStream) {
        HttpRequest httpRequest = new HttpRequest();

        HttpRequestParser.parse(inputStream, httpRequest);

        assertThrows(NotFoundException.class, () -> {
            HandlerMapping.getHandler(httpRequest);
        });
    }

    static Stream<Arguments> createInvalidLocationRequest() {
        String str = "GET /없는경로입니다. HTTP/1.1\n\r";

        return Stream.of(
                Arguments.of(new ByteArrayInputStream(str.getBytes()))
        );
    }
}