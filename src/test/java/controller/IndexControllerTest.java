package controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import common.TestFileIo;
import exception.HttpRequestMethodNotSupportedException;
import http.request.Request;
import http.response.Response;

class IndexControllerTest {
    @DisplayName("/경로가 들어왔을 때 index.html을 반환하는 테스트")
    @Test
    void doGetTest() throws Exception {
        Request request = new Request(TestFileIo.readBufferedReader("http_index_request.txt"));
        Response response = new Response(TestFileIo.createOutputStream("http_index_response.txt"));

        IndexController indexController = new IndexController();
        indexController.doGet(request, response);

        BufferedReader responseBr = TestFileIo.readBufferedReader("http_index_response.txt");

        assertThat(responseBr.readLine()).isEqualTo("HTTP/1.1 200 OK ");
        responseBr.readLine();
        assertThat(responseBr.readLine()).isEqualTo("Content-Type: text/html;charset=UTF-8 ");
    }

    @DisplayName("POST 메서드로 들어올 경우 예외처리")
    @Test
    void doPostTest() throws Exception {
        Request request = new Request(TestFileIo.readBufferedReader("http_post_index_request.txt"));
        Response response = new Response(TestFileIo.createOutputStream("http_post_index_response.txt"));

        IndexController indexController = new IndexController();
        assertThatThrownBy(() -> indexController.doPost(request, response))
                .isInstanceOf(HttpRequestMethodNotSupportedException.class);

        BufferedReader responseBr = TestFileIo.readBufferedReader("http_post_index_response.txt");

        assertAll(
                () -> assertThat(responseBr.readLine()).isEqualTo("HTTP/1.1 405 Method Not Allowed "),
                () -> assertThat(responseBr.readLine()).isEmpty(),
                () -> assertThat(responseBr.readLine()).isEqualTo("Request method POST not supported")
        );
    }
}
