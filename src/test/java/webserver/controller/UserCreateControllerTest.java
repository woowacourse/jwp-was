package webserver.controller;

import static org.assertj.core.api.Assertions.assertThat;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserCreateControllerTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("UserCreateController 회원가입 후 리다이렉트 테스트")
    void doPost() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest httpRequest = HttpRequest.from(in);
        OutputStream out = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(out, httpRequest.version());
        new UserCreateController().doPost(httpRequest, httpResponse);
        assertThat(out.toString()).isEqualTo("HTTP/1.1 302 Found\nLocation: /index.html\n");
    }
}
