import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static test.IoUtil.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.CreateUserController;
import controller.ListController;
import controller.RootController;
import controller.UserLoginController;
import domain.response.HttpServletResponse;
import dto.UrlMappingCreateDto;
import servlet.HttpRequest;
import servlet.HttpResponse;
import util.HandlebarsTemplateEngine;

class FrontControllerTest {

    private final static FrontController FRONT_CONTROLLER;

    static {
        List<UrlMappingCreateDto> urlMappingCreateDtos = Arrays.asList(
            UrlMappingCreateDto.of("/", new RootController()),
            UrlMappingCreateDto.of("/user/create", new CreateUserController()),
            UrlMappingCreateDto.of("/user/login", new UserLoginController()),
            UrlMappingCreateDto.of("/user/list", new ListController(HandlebarsTemplateEngine.getInstance()))
        );

        UrlMapper instance = UrlMapper.from(urlMappingCreateDtos);
        FRONT_CONTROLLER = FrontController.from(instance);
    }

    @DisplayName("FrontController에서 지원하지 않는 Method 요청 시 405 응답보낸다.")
    @Test
    void methodNotAllowed() throws IOException {
        HttpRequest httpRequest = createRequest("http_with_method_not_allowed.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/method_not_allowed.txt"));

        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = readFile("/out/method_not_allowed.txt");
        assertThat(actual).contains("405");
    }

    @DisplayName("FrontController에서 지원하지 않는 URL 요청 시 404 응답보낸다.")
    @Test
    void pageNotFound() throws IOException {
        HttpRequest httpRequest = createRequest("http_not_supported_url.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/page_not_found.txt"));

        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = readFile("/out/page_not_found.txt");
        assertThat(actual).contains("404");
    }

    @DisplayName("FrontController에서 URL에 맞는 정적 파일이 잘 나오는지 확인한다.")
    @Test
    void mapStaticFileUrl() throws IOException {
        HttpRequest httpRequest = createRequest("http_template_request.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/index_page.txt"));
        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = readFile("/out/index_page.txt");
        String expected = readFile("/index.html", "./src/main/resources/templates");
        assertAll(
            () -> assertThat(actual).contains("200"),
            () -> assertThat(actual).contains(expected)
        );
    }

    @DisplayName("FrontController에서 root URL을 요청했을 때 상태코드와 내용이 잘 나오는지 확인한다.")
    @Test
    void mapRootUrl() throws IOException {
        HttpRequest httpRequest = createRequest("http_root.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/root_page.txt"));
        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = readFile("/out/root_page.txt");
        String expected = readFile("/index.html", "./src/main/resources/templates");
        assertAll(
            () -> assertThat(actual).contains("200"),
            () -> assertThat(actual).contains(expected)
        );
    }

    @DisplayName("FrontController에서 api 요청 시 상태코드와 내용이 잘 나오는지 확인한다.")
    @Test
    void mapApi() throws IOException {
        HttpRequest httpRequest = createRequest("http_post_with_formdata.txt");
        HttpResponse httpResponse = new HttpServletResponse(createOutputStream("/out/create_user_response.txt"));
        FRONT_CONTROLLER.service(httpRequest, httpResponse);

        String actual = readFile("/out/create_user_response.txt");
        assertAll(
            () -> assertThat(actual).contains("302"),
            () -> assertThat(actual).contains("Location: /index.html")
        );
    }
}
