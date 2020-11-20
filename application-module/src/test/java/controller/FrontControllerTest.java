package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import common.IoUtil;
import domain.UrlMapper;
import domain.controller.Controller;
import domain.controller.FrontController;
import domain.request.HttpRequest;
import domain.response.HttpResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FrontControllerTest {

    private final static FrontController FRONT_CONTROLLER;

    static {
        Map<String, Controller> urlMapper = new HashMap<>();
        urlMapper.put("/user/create", CreateUserController.getInstance());
        urlMapper.put("/user/login", UserLoginController.getInstance());
        urlMapper.put("/", RootController.getInstance());
        urlMapper.put("/user/list", ListController.getInstance());
        FRONT_CONTROLLER = new FrontController(new UrlMapper(urlMapper));
    }

    @DisplayName("FrontController에서 지원하지 않는 Method 요청 시 405 응답보낸다.")
    @Test
    void methodNotAllowed() throws IOException {
        HttpRequest httpRequest = IoUtil.createRequest("http_with_method_not_allowed.txt");
        HttpResponse httpResponse = new HttpResponse(IoUtil.createOutputStream("/out/method_not_allowed.txt"));

        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = IoUtil.readFile("/out/method_not_allowed.txt");
        assertThat(actual).contains("405");
    }

    @DisplayName("FrontController에서 지원하지 않는 URL 요청 시 404 응답보낸다.")
    @Test
    void pageNotFound() throws IOException {
        HttpRequest httpRequest = IoUtil.createRequest("http_not_supported_url.txt");
        HttpResponse httpResponse = new HttpResponse(IoUtil.createOutputStream("/out/page_not_found.txt"));

        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = IoUtil.readFile("/out/page_not_found.txt");
        assertThat(actual).contains("404");
    }

    @DisplayName("FrontController에서 URL에 맞는 정적 파일이 잘 나오는지 확인한다.")
    @Test
    void mapStaticFileUrl() throws IOException {
        HttpRequest httpRequest = IoUtil.createRequest("http_template_request.txt");
        HttpResponse httpResponse = new HttpResponse(IoUtil.createOutputStream("/out/index_page.txt"));
        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = IoUtil.readFile("/out/index_page.txt");
        String expected = IoUtil.readFile("/index.html", "./src/main/resources/templates");
        assertAll(
            () -> assertThat(actual).contains("200"),
            () -> assertThat(actual).contains(expected)
        );
    }

    @DisplayName("FrontController에서 root URL을 요청했을 때 상태코드와 내용이 잘 나오는지 확인한다.")
    @Test
    void mapRootUrl() throws IOException {
        HttpRequest httpRequest = IoUtil.createRequest("http_root.txt");
        HttpResponse httpResponse = new HttpResponse(IoUtil.createOutputStream("/out/root_page.txt"));
        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = IoUtil.readFile("/out/root_page.txt");
        String expected = IoUtil.readFile("/index.html", "./src/main/resources/templates");
        assertAll(
            () -> assertThat(actual).contains("200"),
            () -> assertThat(actual).contains(expected)
        );
    }

    @DisplayName("FrontController에서 api 요청 시 상태코드와 내용이 잘 나오는지 확인한다.")
    @Test
    void mapApi() throws IOException {
        HttpRequest httpRequest = IoUtil.createRequest("http_post_with_formdata.txt");
        HttpResponse httpResponse = new HttpResponse(IoUtil.createOutputStream("/out/create_user_response.txt"));
        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = IoUtil.readFile("/out/create_user_response.txt");
        assertAll(
            () -> assertThat(actual).contains("302"),
            () -> assertThat(actual).contains("Location: /index.html")
        );
    }
}
