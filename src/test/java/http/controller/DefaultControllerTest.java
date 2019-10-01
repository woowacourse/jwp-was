package http.controller;

import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RequestClientTest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultControllerTest {
    private RequestClientTest requestClient;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    @DisplayName("요청 path가 없는 경우")
    public void doGetTest() throws Exception {
        requestClient = RequestClientTest.get("/test");

        InputStream in = new ByteArrayInputStream(requestClient.toString().getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = RequestHandler.getInstance().create(br);
        httpResponse = ResponseHandler.getInstance().create(httpRequest);

        DefaultController defaultController = new DefaultController();
        defaultController.doGet(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("405 Method Not Allowed");
    }

    @Test
    @DisplayName("요청 path가 post 없는 경우")
    public void doPostTest() throws Exception {
        requestClient = RequestClientTest.get("/test");

        InputStream in = new ByteArrayInputStream(requestClient.toString().getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = RequestHandler.getInstance().create(br);
        httpResponse = ResponseHandler.getInstance().create(httpRequest);

        DefaultController defaultController = new DefaultController();
        defaultController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("405 Method Not Allowed");
    }
}