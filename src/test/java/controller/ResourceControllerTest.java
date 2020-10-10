package controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import common.TestFileIo;
import http.request.Request;
import http.response.Response;

class ResourceControllerTest {

    @DisplayName("/index.html을 요청했을 때 index.html을 반환하는 테스트")
    @Test
    void doGetTest() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Request request = new Request(TestFileIo.readBufferedReader("http_resource_request.txt"));
        Response response = new Response(result);

        ResourceController resourceController = new ResourceController();
        resourceController.doGet(request, response);
    
        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 200 OK "),
                () -> assertThat(actual).contains("Content-Type: text/html;charset=UTF-8 ")
        );
    }
}
