package http.controller;

import db.DataBase;
import http.request.HttpRequest;
import http.request.RequestHandler;
import http.response.HttpResponse;
import http.response.ResponseHandler;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    public void doPostTest() throws Exception {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST_Login.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        httpRequest = new RequestHandler(br).create();
        httpResponse = new ResponseHandler().create();

        User user = new User("cony", "password", "cony", "cony@cony.com");
        DataBase.addUser(user);

        LoginController loginController = new LoginController();
        loginController.doPost(httpRequest, httpResponse);

        assertThat(httpResponse.toString()).contains("302 FOUND");
        assertThat(httpResponse.toString()).contains("Location: /index.html");
        assertThat(httpResponse.toString()).contains("JSESSIONID");
    }
}