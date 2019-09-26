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
    }

    @Test
    void SignUpController_선택() {
        String request = "GET /user/create?key=value HTTP/1.1";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        assertThat(handlers.doService(httpRequest).getHeader(LOCATION)).isEqualTo("./templates/index.html");
    }

    @Test
    void FileResourceController_선택_index() {
        String request = "GET /index.html HTTP/1.1";
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