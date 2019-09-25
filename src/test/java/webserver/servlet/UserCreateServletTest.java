package webserver.servlet;

import helper.IOHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateServletTest {
    @DisplayName("유저 생성")
    @Test
    void doPost_userDataByBody_redirect() throws IOException {
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
        HttpRequest httpRequest = HttpRequestParser.parse(bufferedReader);
        UserCreateServlet userCreateServlet = new UserCreateServlet();
        byte[] body = null;
        Map<String, Object> header = new HashMap<>();
        header.put("Location", "/index.html");
        assertThat(userCreateServlet.doPost(httpRequest)).isEqualTo(new HttpResponse(HttpStatus.FOUND, header, body));
    }
}