package controller;

import static org.assertj.core.api.Assertions.*;

import java.io.BufferedReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import common.TestFileIo;
import http.request.Request;
import http.response.Response;

class UserCreateControllerTest {

    @DisplayName("/users를 get으로 요청했을때 index로 found 되는지 테스트")
    @Test
    void doGetTest() throws Exception {
        Request request = new Request(TestFileIo.readBufferedReader("http_user_get_request.txt"));
        Response response = new Response(TestFileIo.createOutputStream("http_user_get_response.txt"));

        UserCreateController userCreateController = new UserCreateController();
        userCreateController.doGet(request, response);

        BufferedReader responseBr = TestFileIo.readBufferedReader("http_user_get_response.txt");

        assertThat(responseBr.readLine()).isEqualTo("HTTP/1.1 302 Found ");
        assertThat(responseBr.readLine()).isEqualTo("Location: /index.html ");
    }

    @DisplayName("/users를 post으로 요청했을때 index로 found 되는지 테스트")
    @Test
    void doPostTest() throws Exception {
        Request request = new Request(TestFileIo.readBufferedReader("http_user_post_request.txt"));
        Response response = new Response(TestFileIo.createOutputStream("http_user_post_response.txt"));

        UserCreateController userCreateController = new UserCreateController();
        userCreateController.doPost(request, response);

        BufferedReader responseBr = TestFileIo.readBufferedReader("http_user_post_response.txt");

        assertThat(responseBr.readLine()).isEqualTo("HTTP/1.1 302 Found ");
        assertThat(responseBr.readLine()).isEqualTo("Location: /index.html ");
    }
}
