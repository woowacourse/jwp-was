package controller;

import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import http.response.RedirectResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PostUserControllerTest {

    @Test
    @DisplayName("POST /user/create 요청 보낼 시 RedirectResponse 생성 확인")
    void createPostUserResponse(){
        RequestMethod method = RequestMethod.POST;
        RequestUrl url = RequestUrl.from("/user/create");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "POST /user/create HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        information.put("Content-Length:", "59");
        information.put("Content-Type:", "application/x-www-form-urlencoded");
        information.put("Accept:", "*/*");
        information.put("Query-Parameters:", "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");


        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);

        ControllerFactory factory = new ControllerFactory();
        Controller controller = factory.createController(request);

        assertThat(controller.createResponse(request).getClass()).isEqualTo(RedirectResponse.class);
    }
}
