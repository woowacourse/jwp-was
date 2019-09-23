package controller;

import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerFactoryTest {

    @Test
    @DisplayName("get request 넣었을 시 원하는 Controller가 나오는지 테스트")
    void createFileController() {
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/index.html");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /index.html HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);

        ControllerFactory factory = new ControllerFactory();
        assertThat(factory.createController(request).getClass()).isEqualTo(FileController.class);
    }

    @Test
    @DisplayName("POST /user/create request를 보낼시 원하는 Controller가 나오는지 테스트")
    void createUserController() {
        RequestMethod method = RequestMethod.POST;
        RequestUrl url = RequestUrl.from("/user/create");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "POST /user/create HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        information.put("Content-Length:", "59");
        information.put("Content-Type:", "application/x-www-form-urlencoded");
        information.put("Accept:", "*/*");
        information.put("Query-Parameters", "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");


        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);

        ControllerFactory factory = new ControllerFactory();
        assertThat(factory.createController(request).getClass()).isEqualTo(UserController.class);
    }

    @Test
    @DisplayName("이상한 url롤 요청을 보냈을 시 ErrorController가 나오는지 확인")
    void createExceptionController() {
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/friend");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /friend HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        information.put("Content-Length:", "59");
        information.put("Content-Type:", "application/x-www-form-urlencoded");
        information.put("Accept:", "*/*");
        information.put("Query-Parameters", "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");


        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);

        ControllerFactory factory = new ControllerFactory();
        assertThat(factory.createController(request).getClass()).isEqualTo(ExceptionController.class);
    }
}
