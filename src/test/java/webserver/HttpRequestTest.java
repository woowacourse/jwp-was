package webserver;

import helper.IOHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.parser.HttpRequestParser;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    final String INDEX_URL = "/index.html";

    @DisplayName("절대경로 확인")
    @Test
    void getPath_indexUrl_true() throws IOException {
        BufferedReader bufferedReader = IOHelper.createBuffer(
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html,*/*"
        );
        HttpRequest request = HttpRequestParser.parse(bufferedReader);
        assertThat(request.getAbsPath()).isEqualTo(INDEX_URL);
    }

    @DisplayName("파라미터 파싱 확인")
    @Test
    void getParameter_userInfo_true() throws IOException {
        BufferedReader bufferedReader = IOHelper.createBuffer(
                "GET /user/create?userId=id&password=password&name=gyu HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html,*/*"
        );
        HttpRequest request = HttpRequestParser.parse(bufferedReader);
        assertThat(request.getParam("userId")).isEqualTo("id");
        assertThat(request.getParam("password")).isEqualTo("password");
        assertThat(request.getParam("name")).isEqualTo("gyu");
    }

    @DisplayName("메소드 얻기 확인")
    @Test
    void getMethod_post_true() throws IOException {
        BufferedReader bufferedReader = IOHelper.createBuffer(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: text/html,*/*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
        HttpRequest request = HttpRequestParser.parse(bufferedReader);
        assertThat(request.getMethod()).isEqualTo(RequestMethod.POST);
    }

    @DisplayName("바디 파싱 확인")
    @Test
    void getBody_userId_true() throws IOException {
        BufferedReader bufferedReader = IOHelper.createBuffer(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: text/html,*/*",
                "",
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"
        );
        HttpRequest request = HttpRequestParser.parse(bufferedReader);
        assertThat(request.getBody("userId")).isEqualTo("javajigi");
        assertThat(request.getBody("password")).isEqualTo("password");
    }
}