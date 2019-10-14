package http.controller;

import http.model.*;
import http.supoort.HttpRequestParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static com.google.common.net.HttpHeaders.SET_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    @BeforeEach
    void setUp() {
        String request = "POST /user/create?name=hyogeon HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 46\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n" +
                "\r\n" +
                "userId=coogie&password=coogie&email=a@b.c";
        HttpRequest httpRequest = HttpRequestParser.parse(new ByteArrayInputStream(request.getBytes()));

        Controller controller = new SignUpController();
        assertThat(controller.service(httpRequest).getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void 로그인_성공() {
        Controller controller = new LoginController();
        String requestMessage = "POST /user/login HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpParameters httpParameters = new HttpParameters();
        httpParameters.addParameter("userId", "coogie");
        httpParameters.addParameter("password", "coogie");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, httpParameters, httpHeaders);

        HttpResponse httpResponse = controller.service(httpRequest);
        assertThat(httpResponse.getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void 아이디_없는_경우() {
        Controller controller = new LoginController();
        String requestMessage = "POST /user/login HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpParameters httpParameters = new HttpParameters();
        httpParameters.addParameter("userId", "coogi");
        httpParameters.addParameter("password", "coogie");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, httpParameters, httpHeaders);

        HttpResponse httpResponse = controller.service(httpRequest);
        assertThat(httpResponse.getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeader(SET_COOKIE)).isNull();
    }

    @Test
    void 비밀번호_틀린_경우() {
        Controller controller = new LoginController();
        String requestMessage = "POST /user/login HTTP/1.1";
        RequestLine requestLine = new RequestLine(requestMessage);
        HttpParameters httpParameters = new HttpParameters();
        httpParameters.addParameter("userId", "coogie");
        httpParameters.addParameter("password", "coogi");
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, httpParameters, httpHeaders);

        HttpResponse httpResponse = controller.service(httpRequest);
        assertThat(httpResponse.getStatusLine().getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeader(SET_COOKIE)).isNull();
    }
}