package controller;

import http.request.HttpRequest;
import http.request.RequestFactory;
import http.response.HttpResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import view.ModelAndView;
import webserver.ServerErrorException;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SignUpControllerTest {
    private String requestLines;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    private SignUpController signUpController = new SignUpController();
    private ModelAndView modelAndView;

    @Test
    void GET_회원가입_성공() throws IOException {
        requestLines = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);
        modelAndView = signUpController.service(httpRequest, httpResponse);

        assertThat(modelAndView.getView()).isEqualTo("/index.html");
        assertThat(modelAndView.isRedirect()).isEqualTo(true);
    }

    @Test
    void GET_회원가입_실패_파라미터_없음() throws IOException {
        requestLines = "GET /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);

        assertThrows(ServerErrorException.class, () -> signUpController.service(httpRequest, httpResponse));
    }

    @Test
    void POST_회원가입_성공() throws IOException {
        requestLines = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "Content-Length: 93\n" +
                "\r\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);
        modelAndView = signUpController.service(httpRequest, httpResponse);

        assertThat(modelAndView.getView()).isEqualTo("/index.html");
        assertThat(modelAndView.isRedirect()).isEqualTo(true);
    }

    @Test
    void POST_회원가입_실패_바디없음() throws IOException {
        requestLines = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "\r\n";
        InputStream in = IOUtils.toInputStream(requestLines, "UTF-8");

        httpRequest = RequestFactory.createHttpRequest(in);
        httpResponse = new HttpResponse(httpRequest);

        assertThrows(ServerErrorException.class, () -> signUpController.service(httpRequest, httpResponse));
    }
}
