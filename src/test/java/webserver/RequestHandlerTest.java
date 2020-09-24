package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.domain.request.HttpRequest;
import webserver.domain.response.HttpResponse;

class RequestHandlerTest {
    @DisplayName("정적 컨텐츠 처리에 대한 요청에 응답한다.")
    @Test
    void controlRequestAndResponse_whenRequestIsForStaticContent() throws
        URISyntaxException,
        InstantiationException,
        IllegalAccessException,
        IOException {
        RequestHandler requestHandler = new RequestHandler(new Socket());
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/TemplatesResourceRequest.txt"));
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(httpResponse.getHeader()).contains("Content-Type: text/html;charset=utf-8", "Content-Length: 6902");
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @DisplayName("동적 컨텐츠 처리에 대한 요청에 응답한다.")
    @Test
    void controlRequestAndResponse_whenRequestIsForDynamicContent() throws
        URISyntaxException,
        InstantiationException,
        IllegalAccessException,
        IOException {
        RequestHandler requestHandler = new RequestHandler(new Socket());
        InputStream inputStream = new FileInputStream(
            new File("/Users/moon/Desktop/Github/jwp-was/build/resources/test/GetRequest.txt"));
        HttpRequest httpRequest = HttpRequest.of(inputStream);

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(httpResponse.getHeader()).contains("Location: /index.html");
        assertThat(httpResponse.getBody()).isEqualTo(new byte[0]);
        assertThat(DataBase.findUserById("javajigi")).isEqualToComparingFieldByField(
            new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }
}