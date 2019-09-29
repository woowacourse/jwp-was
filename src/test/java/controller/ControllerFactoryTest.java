package controller;

import controller.controllermapper.ControllerFactory;
import http.request.Request;
import http.request.RequestUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerFactoryTest extends BaseTest {
    private ControllerFactory factory = new ControllerFactory();

    @Test
    @DisplayName("get request 넣었을 시 원하는 Controller가 나오는지 테스트")
    void mapFileController() {

        //given
        RequestUrl url = RequestUrl.from("/index.html");
        List<String> headerValues = Arrays.asList("GET /index.html HTTP/1.1");

        //when
        Request request = createGetRequest(url, headerValues);

        //then
        assertThat(factory.mappingController(request).getClass()).isEqualTo(FileController.class);
    }


    @Test
    @DisplayName("POST /user/create request를 보낼시 원하는 Controller가 나오는지 테스트")
    void mapUserController() {

        //given
        RequestUrl url = RequestUrl.from("/user/create");
        List<String> headerValues = Arrays.asList("POST /user/create HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        //when
        Request request = createPostRequest(url, headerValues);

        //then
        assertThat(factory.mappingController(request).getClass()).isEqualTo(CreateUserController.class);
    }

    @Test
    @DisplayName("GET /user/list request를 보낼시 원하는 Controller가 나오는지 테스트")
    void mapUserController2() {

        //given
        RequestUrl url = RequestUrl.from("/user/list");
        List<String> headerValues = Arrays.asList("GET /user/list HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        //when
        Request request = createGetRequest(url, headerValues);

        //then
        assertThat(factory.mappingController(request).getClass()).isEqualTo(GetUserListController.class);
    }


    @Test
    @DisplayName("이상한 url롤 요청을 보냈을 시 ErrorController가 나오는지 확인")
    void mapExceptionController() {
        //given
        RequestUrl url = RequestUrl.from("/friend");
        List<String> headerValues = Arrays.asList("GET /friend HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        //when
        Request request = createGetRequest(url, headerValues);

        //then
        assertThat(factory.mappingController(request).getClass()).isEqualTo(ExceptionController.class);
    }

    @Test
    @DisplayName("로그인 요청시 LoginController 나오는지 테스트")
    void mapLoginController() {

        //give
        RequestUrl url = RequestUrl.from("/user/login");
        List<String> headerValues = Arrays.asList("POST /user/login HTTP/1.1", "59",
                "application/x-www-form-urlencoded",
                "userId=javajigi&password=password");
        //when
        Request request = createPostRequest(url, headerValues);

        //then
        assertThat(factory.mappingController(request).getClass()).isEqualTo(LoginController.class);
    }
}
