package controller;

import common.TestFileIo;
import http.request.Request;
import http.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceControllerTest {

    @DisplayName("/index.html을 요청했을 때 index.html을 반환하는 테스트")
    @Test
    void doGetTest() throws Exception {
        Request request = new Request(TestFileIo.readBufferedReader("http_resource_request.txt"));
        Response response = new Response(TestFileIo.createOutputStream("http_resource_response.txt"));

        ResourceController resourceController = new ResourceController();
        resourceController.doGet(request, response);

        BufferedReader responseBr = TestFileIo.readBufferedReader("http_resource_response.txt");

        assertThat(responseBr.readLine()).isEqualTo("HTTP/1.1 200 OK ");
        assertThat(responseBr.readLine()).isEqualTo("Content-Length: 7049 ");
        assertThat(responseBr.readLine()).isEqualTo("Content-Type: text/html;charset=UTF-8 ");
    }
}
