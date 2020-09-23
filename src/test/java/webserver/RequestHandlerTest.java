package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.domain.Header;
import webserver.domain.request.HttpRequest;
import webserver.domain.request.RequestLine;
import webserver.domain.response.HttpResponse;

class RequestHandlerTest {
    private static final String lineSeparator = System.lineSeparator();

    @DisplayName("정적 컨텐츠 처리에 대한 요청에 응답한다.")
    @Test
    void controlRequestAndResponse_whenRequestIsForStaticContent() throws
        URISyntaxException,
        InstantiationException,
        IllegalAccessException,
        IOException {
        RequestHandler requestHandler = new RequestHandler(new Socket());
        RequestLine requestLine = RequestLine.of("GET /index.html HTTP/1.1");
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Host", "localhost:8080");
        headerFields.put("Connection", "keep-alive");
        headerFields.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        Header header = new Header(headerFields);
        HttpRequest httpRequest = new HttpRequest(requestLine, header, "");
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

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
        RequestLine requestLine = RequestLine.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1");
        Map<String, String> headerFields = new HashMap<>();
        headerFields.put("Host", "localhost:8080");
        headerFields.put("Connection", "keep-alive");
        headerFields.put("Accept", "*/*");
        Header header = new Header(headerFields);
        HttpRequest httpRequest = new HttpRequest(requestLine, header, "");

        HttpResponse httpResponse = requestHandler.controlRequestAndResponse(httpRequest);

        assertThat(httpResponse.getStatusLine().getValue()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(httpResponse.getHeader()).contains("Location: /index.html");
        assertThat(httpResponse.getBody()).isEqualTo(new byte[0]);
        assertThat(DataBase.findUserById("javajigi")).isEqualToComparingFieldByField(new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net"));
    }
}