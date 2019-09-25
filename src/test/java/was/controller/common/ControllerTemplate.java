package was.controller.common;

import org.junit.jupiter.api.BeforeEach;
import was.controller.Controller;
import was.controller.CreateUserController;
import was.controller.LoginUserController;
import was.exception.MethodNotAllowedException;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerTemplate {
    private static final String USER_INFO = "userId=ddu0422&password=1234&name=mir&email=ddu0422@naver.com";
    private static final String ANOTHER_USER_INFO = "userId=pkch&password=1234&name=chelsea&email=pkch93@gmail.com";
    private static final String CONTENT_LENGTH = String.valueOf(USER_INFO.length());

    protected HttpRequest httpRequest;
    protected HttpResponse httpResponse;

    @BeforeEach
    public void setUp() throws Exception {
        httpRequest = new HttpRequest();
        httpResponse = new HttpResponse();
    }

    public void signUp() throws Exception {
        String signUp = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: " + CONTENT_LENGTH + "\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                USER_INFO + "\n\r";

        InputStream inputStream = new ByteArrayInputStream(signUp.getBytes());;
        HttpRequestParser.parse(inputStream, httpRequest);
        Controller controller = CreateUserController.getInstance();
        controller.service(httpRequest, httpResponse);
    }

    public void signIn() throws Exception {
        String signIn =
                "POST /user/login HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: " + CONTENT_LENGTH + "\n" +
                        "Accept: */*\n" +
                        "Cookie: logined=true\n" +
                        "\n" +
                        USER_INFO + "\n\r";

        InputStream inputStream = new ByteArrayInputStream(signIn.getBytes());
        HttpRequestParser.parse(inputStream, httpRequest);
        Controller controller = LoginUserController.getInstance();
        controller.service(httpRequest, httpResponse);
    }

    public void singInFailed() throws Exception {
        String signIn =
                "POST /user/login HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: " + CONTENT_LENGTH + "\n" +
                        "Accept: */*\n" +
                        "Cookie: logined=true\n" +
                        "\n" +
                        ANOTHER_USER_INFO + "\n\r";

        InputStream inputStream = new ByteArrayInputStream(signIn.getBytes());
        HttpRequestParser.parse(inputStream, httpRequest);
        Controller controller = LoginUserController.getInstance();
        controller.service(httpRequest, httpResponse);
    }

    public void allowedNotMethod(HttpMethod httpMethod, Controller controller) throws Exception {
        String notAllowedMethod = httpMethod.name() + " /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*\n\r";

        InputStream inputStream = new ByteArrayInputStream(notAllowedMethod.getBytes());
        HttpRequestParser.parse(inputStream, httpRequest);

        assertThrows(MethodNotAllowedException.class, () -> {
            controller.service(httpRequest, httpResponse);
        });
    }
}
