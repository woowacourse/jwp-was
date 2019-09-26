package http.controller;

import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultControllerTest {
    private static final String REQUEST_STRING =
            "GET /test HTTP/1.1" + "\r\n" +
            "Host: localhost:8080" + "\r\n" +
            "Connection: keep-alive" + "\r\n" +
            "\r\n";

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    public void doPostTest() throws Exception {
        InputStream in = new ByteArrayInputStream(REQUEST_STRING.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
        httpResponse = new ResponseHandler().create(httpRequest);

        DefaultController defaultController = new DefaultController();
        defaultController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("404 NOT FOUND");
    }
}