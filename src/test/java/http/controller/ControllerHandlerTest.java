package http.controller;

import http.model.HttpMethod;
import http.model.HttpRequest;
import http.model.HttpStatus;
import http.supoort.ControllerMapping;
import http.supoort.HttpRequestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static com.google.common.net.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

class ControllerHandlerTest {
    private ControllerHandler handlers = new ControllerHandler();
    private FileResourceController fileResourceController;
    private SignUpController signUpController;

    @BeforeEach
    void setUp() {
        fileResourceController = new FileResourceController();
        signUpController = new SignUpController();
        handlers.addController(new ControllerMapping(HttpMethod.GET, "/*"), fileResourceController);
        handlers.addController(new ControllerMapping(HttpMethod.GET, "/user/create"), signUpController);
        handlers.addController(new ControllerMapping(HttpMethod.POST, "/user/create"), signUpController);
    }

    @Test
    void SignUpController_선택_GET() {
        String request = "GET /user/create?key=value HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getHeader(LOCATION)).isEqualTo("./templates/index.html");
    }

    @Test
    void SignUpController_선택_POST() {
        String request = "POST /user/create?name=JasSung HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 46\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                " \r\n" +
                "userId=javajigi&password=password&email=a@b.c\r\n";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getHeader(LOCATION)).isEqualTo("./templates/index.html");
    }

    @Test
    void FileResourceController_선택_index() {
        String request = "GET /index.html HTTP/1.1\r\n";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void FileResourceController_선택_form() {
        String request = "GET /user/form.html HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.OK);
    }
}