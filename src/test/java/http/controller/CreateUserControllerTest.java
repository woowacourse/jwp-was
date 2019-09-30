package http.controller;

import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import org.junit.jupiter.api.Test;
import utils.RequestClientTest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {
    private RequestClientTest requestClient;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    public void doPostTest() throws Exception {
        requestClient = RequestClientTest.post("/user/create")
                .setFormBody("userId=aiden&password=password&name=aiden&email=aiden@aiden.com");

        InputStream in = new ByteArrayInputStream(requestClient.toString().getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = RequestHandler.getInstance().create(br);
        httpResponse = ResponseHandler.getInstance().create(httpRequest);

        CreateUserController createUserController = new CreateUserController();
        createUserController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("302 FOUND");
        assertThat(httpResponse.toString()).contains("Location: /index.html");
    }
}