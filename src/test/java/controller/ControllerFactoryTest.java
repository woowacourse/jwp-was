package controller;

import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerFactoryTest extends BaseTest {

    @Test
    @DisplayName("get request 넣었을 시 원하는 Controller가 나오는지 테스트")
    void mapFileController() throws IOException, URISyntaxException {
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/index.html");
        Map<String, String > header = makeGetHeader("GET /index.html HTTP/1.1");


        RequestInformation requestInformation = new RequestInformation(header);
        Request request = new Request(method, url, requestInformation);
        ControllerFactory factory = new ControllerFactory();

        assertThat(factory.mappingController(request).getClass()).isEqualTo(FileController.class);
    }

    @Test
    @DisplayName("POST /user/create request를 보낼시 원하는 Controller가 나오는지 테스트")
    void mapUserController() {
        RequestMethod method = RequestMethod.POST;
        RequestUrl url = RequestUrl.from("/user/create");
        Map<String, String > header = makePostHeader("POST /user/create HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        RequestInformation requestInformation = new RequestInformation(header);
        Request request = new Request(method, url, requestInformation);
        ControllerFactory factory = new ControllerFactory();

        assertThat(factory.mappingController(request).getClass()).isEqualTo(UserController.class);
    }

    @Test
    @DisplayName("이상한 url롤 요청을 보냈을 시 ErrorController가 나오는지 확인")
    void mapExceptionController() {
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/friend");
        Map<String, String > header = makePostHeader("GET /friend HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");


        RequestInformation requestInformation = new RequestInformation(header);
        Request request = new Request(method, url, requestInformation);
        ControllerFactory factory = new ControllerFactory();

        assertThat(factory.mappingController(request).getClass()).isEqualTo(ExceptionController.class);
    }

    @Test
    @DisplayName("로그인 요청시 LoginController 나오는지 테스트")
    void mapLoginController() {
        RequestMethod method = RequestMethod.POST;
        RequestUrl url = RequestUrl.from("/user/login");
        Map<String, String > header = makePostHeader("POST /user/login HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=javajigi&password=password");


        RequestInformation requestInformation = new RequestInformation(header);
        Request request = new Request(method, url, requestInformation);
        ControllerFactory factory = new ControllerFactory();

        assertThat(factory.mappingController(request).getClass()).isEqualTo(LoginController.class);
    }
}
