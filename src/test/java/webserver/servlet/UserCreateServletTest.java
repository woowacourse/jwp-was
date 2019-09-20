package webserver.servlet;

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
    private String testDirectory = "./src/test/resources/";

    @Test
    void doPost() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_post_test.txt"));
        HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        UserCreateServlet userCreateServlet = new UserCreateServlet();
        byte[] body = null;
        Map<String, Object> header = new HashMap<>();
        header.put("Location", "/index.html");
        assertThat(userCreateServlet.doPost(httpRequest)).isEqualTo(new HttpResponse(HttpStatus.FOUND, header, body));
    }
}