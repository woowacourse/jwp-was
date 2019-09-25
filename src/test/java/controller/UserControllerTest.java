package controller;

import db.DataBase;
import http.ResponseTest;
import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import http.response.Response;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {

    @Test
    @DisplayName("POST /user/create 요청 보낼 시 RedirectResponse 생성 확인")
    void createPostUserResponse() throws IOException, URISyntaxException {
        RequestMethod method = RequestMethod.POST;
        RequestUrl url = RequestUrl.from("/user/create");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "POST /user/create HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        information.put("Content-Length:", "59");
        information.put("Content-Type:", "application/x-www-form-urlencoded");
        information.put("Accept:", "*/*");
        information.put("Query-Parameters:", "userId=javajigi&password=password&name=이인권&email=podo@gmail.com");


        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);
        Response response = new Response();

        ControllerFactory factory = new ControllerFactory();
        Controller controller = factory.mappingController(request);
        controller.processResponse(request, response);

        assertThat(DataBase.findUserById("javajigi")).isEqualTo(new User("javajigi", "password", "이인권", "podo@gmail.com"));
    }
}
