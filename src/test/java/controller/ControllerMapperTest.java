package controller;

import controller.custom.UserController;
import controller.resources.ResourceController;
import controller.resources.TemplateController;
import org.junit.jupiter.api.Test;
import utils.HttpRequestParser;
import webserver.http.HttpSessionManager;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
class ControllerMapperTest {

    @Test
    void 템플릿_컨트롤러_메서드_리턴_매핑_성공_테스트() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/java/data/HttpRequest.txt");
        InputStreamReader inputStream = new InputStreamReader(fileInputStream);
        HttpRequest httpRequest = HttpRequestParser.parseRequest(inputStream, new HttpSessionManager());
        HttpResponse httpResponse = HttpResponse.of(httpRequest.getHttpVersion());

        Method method = ControllerMapper.mappingMethod(httpRequest, httpResponse);
        assertThat(method.getDeclaringClass()).isEqualTo(TemplateController.class);
    }

    @Test
    void 리소스_컨트롤러_메서드_리턴_매핑_성공_테스트() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/java/data/ResourceFileHttpRequest.txt");
        InputStreamReader inputStream = new InputStreamReader(fileInputStream);
        HttpRequest httpRequest = HttpRequestParser.parseRequest(inputStream, new HttpSessionManager());
        HttpResponse httpResponse = HttpResponse.of(httpRequest.getHttpVersion());

        Method method = ControllerMapper.mappingMethod(httpRequest, httpResponse);
        assertThat(method.getDeclaringClass()).isEqualTo(ResourceController.class);
    }

    @Test
    void 커스텀_컨트롤러_메서드_리턴_매핑_성공_테스트() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/java/data/LoginHttpRequest.txt");
        InputStreamReader inputStream = new InputStreamReader(fileInputStream);
        HttpRequest httpRequest = HttpRequestParser.parseRequest(inputStream, new HttpSessionManager());
        HttpResponse httpResponse = HttpResponse.of(httpRequest.getHttpVersion());

        Method method = ControllerMapper.mappingMethod(httpRequest, httpResponse);
        assertThat(method.getDeclaringClass()).isEqualTo(UserController.class);
    }
}