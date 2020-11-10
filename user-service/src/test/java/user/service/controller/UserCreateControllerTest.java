package user.service.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import http.was.http.request.Request;
import http.was.http.response.Response;
import user.service.common.TestFileIo;

class UserCreateControllerTest {

    @DisplayName("/users를 get으로 요청했을때 index로 found 되는지 테스트")
    @Test
    void doGetTest() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_user_get_request.txt"));
        Response response = new Response(result);

        UserCreateController.doGet(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
                () -> assertThat(actual).contains("Location: /index.html ")
        );
    }

    @DisplayName("/users를 post으로 요청했을때 index로 found 되는지 테스트")
    @Test
    void doPostTest() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_user_post_request.txt"));
        Response response = new Response(result);

        UserCreateController.doPost(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
                () -> assertThat(actual).contains("Location: /index.html ")
        );
    }
}
