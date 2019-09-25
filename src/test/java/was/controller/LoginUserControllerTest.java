package was.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginUserControllerTest {

    @BeforeEach
    @DisplayName("로그인 전 회원가입하기")
    void setUp() throws Exception {
        String str = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 28\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=ddu0422&password=1234\n\r";

        InputStream inputStream = new ByteArrayInputStream(str.getBytes());;

        HttpRequest httpRequest = new HttpRequest();
        HttpResponse httpResponse = new HttpResponse();

        HttpRequestParser.parse(inputStream, httpRequest);

        Controller controller = CreateUserController.getInstance();

        controller.service(httpRequest, httpResponse);
    }


    @ParameterizedTest
    @MethodSource("createLoginRequest")
    void 로그인(InputStream inputStream) throws Exception {
        HttpRequest httpRequest = new HttpRequest();
        HttpResponse httpResponse = new HttpResponse();

        HttpRequestParser.parse(inputStream, httpRequest);

        Controller controller = LoginUserController.getInstance();

        controller.service(httpRequest, httpResponse);

        assertEquals("/user/login.html", httpResponse.getHttpHeader().get("Location"));
        assertEquals("logined=true; Path=/", httpResponse.getHttpHeader().get("Set-Cookie"));
    }

    static Stream<Arguments> createLoginRequest() {
        String str =
                "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 28\n" +
                "Accept: */*\n" +
                "Cookie: logined=true\n" +
                "\n" +
                "userId=ddu0422&password=1234\n\r";

        return Stream.of(
                Arguments.of(new ByteArrayInputStream(str.getBytes()))
        );
    }
}
