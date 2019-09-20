package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;

public class FileControllerTest {
    private static final Logger log = LoggerFactory.getLogger(FileControllerTest.class);
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    void doGet_test() throws IOException, URISyntaxException {
        InputStream in = new ByteArrayInputStream(
                ("GET /index.html HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Accept: */*").getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        httpRequest = HttpRequest.of(in);
        httpResponse = HttpResponse.of(new DataOutputStream(baos));
        baos.toString(); // "HTTP/1.1 200 OK\r\n..."

        FileController fileController = new FileController();

        fileController.doGet(httpRequest, httpResponse);
        log.debug("doGet: {}", baos.toString());
    }

    @Test
    void doGet_test_static() throws IOException, URISyntaxException {
        InputStream in = new ByteArrayInputStream(
                ("GET /css/styles.css HTTP/1.1\r\n" +
                        "Host: localhost:8080\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Accept: */*").getBytes());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        httpRequest = HttpRequest.of(in);
        httpResponse = HttpResponse.of(new DataOutputStream(baos));
        baos.toString(); // "HTTP/1.1 200 OK\r\n..."

        FileController fileController = new FileController();

        fileController.doGet(httpRequest, httpResponse);
        log.debug("doGet: {}", baos.toString());
    }
}
