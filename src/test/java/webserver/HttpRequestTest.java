package webserver;

import helper.IOHelper;
import org.junit.jupiter.api.Test;
import utils.HttpRequestUtils;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.request.RequestMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    final String INDEX_URL = "/index.html";

    @Test
    void getPath_indexUrl_true() throws IOException {
        BufferedReader bufferedReader = IOHelper.createBuffer(
                "GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: text/html,*/*"
        );
        HttpRequest request = HttpRequestParser.parse(bufferedReader);
        assertThat(request.getFilePath()).isEqualTo(HttpRequestUtils.ROOT_TEMPLATE_FILE_PATH + INDEX_URL);
    }

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