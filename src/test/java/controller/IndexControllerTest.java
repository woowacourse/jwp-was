package controller;

import common.TestFileIo;
import exception.HttpRequestMethodNotSupportedException;
import http.request.Request;
import http.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class IndexControllerTest {
    @DisplayName("/경로가 들어왔을 때 index.html을 반환하는 테스트")
    @Test
    void doGetTest() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_index_request.txt"));
        Response response = new Response(result);

        IndexController indexController = new IndexController();
        indexController.doGet(request, response);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 200 OK "),
                () -> assertThat(actual).contains("Content-Type: text/html;charset=UTF-8 ")
        );
    }

    @DisplayName("POST 메서드로 들어올 경우 예외처리")
    @Test
    void doPostTest() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_post_index_request.txt"));
        Response response = new Response(result);

        IndexController indexController = new IndexController();
        assertThatThrownBy(() -> indexController.doPost(request, response))
                .isInstanceOf(HttpRequestMethodNotSupportedException.class);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 405 Method Not Allowed "),
                () -> assertThat(actual).contains("Request method POST not supported")
        );
    }
}
