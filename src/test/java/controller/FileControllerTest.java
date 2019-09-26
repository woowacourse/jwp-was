package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileControllerTest {
    private final String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("파일 읽어오기")
    void doGet() throws IOException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest httpRequest = RequestParser.parse(inputStream);
        HttpResponse httpResponse = new HttpResponse();

        FileController fileController = FileController.getInstance();
        fileController.service(httpRequest, httpResponse);
        assertEquals(httpResponse.getHttpStatusLine().toString(), "HTTP/1.1 200 OK\r\n");
        assertEquals(httpResponse.getHttpResponseHeader().getHeader("Content-Type"), "text/html;charset=utf-8");
        assertEquals(httpResponse.getHttpResponseHeader().getHeader("Content-Length"), "6902");
    }
}