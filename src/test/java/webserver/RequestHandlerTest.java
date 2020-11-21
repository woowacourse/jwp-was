package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import db.UserDataBase;
import model.User;
import utils.FileIoUtils;
import webserver.controller.StaticController;
import webserver.controller.UserCreateController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

class RequestHandlerTest {
    private RequestHandler requestHandler;

    @Mock
    private RequestMapping requestMapping;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        requestHandler = new RequestHandler(new Socket(), requestMapping);
    }

    @DisplayName("정적 컨텐츠 처리에 대한 요청에 응답한다.")
    @Test
    void controlRequestAndResponse_whenRequestIsForStaticContent() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfTemplatesResource();
        given(requestMapping.getController(any(HttpRequest.class))).willReturn(new StaticController());

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(httpResponse.getHeader()).contains("Content-Type: text/html;charset=utf-8", "Content-Length: 6897");
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @DisplayName("동적 컨텐츠 처리에 대한 요청에 응답한다.")
    @Test
    void controlRequestAndResponse_whenRequestIsForDynamicContent() throws IOException {
        HttpRequest httpRequest = HttpRequestFixture.httpRequestOfGetMethod();
        given(requestMapping.getController(any(HttpRequest.class))).willReturn(new UserCreateController());

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(httpResponse.getHeader()).contains("Location: /index.html");
        assertThat(httpResponse.getBody()).isEqualTo(new byte[0]);
        assertThat(UserDataBase.findUserById("javajigi")).isEqualToComparingFieldByField(
            new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }
}