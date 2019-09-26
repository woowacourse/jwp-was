package servlet;

import db.DataBase;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UserServletTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private UserServlet userController = new UserServlet();

    @Test
    void user_생성() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_POST.txt"));
        HttpRequest httpRequest = HttpRequestFactory.makeHttpRequest(in);
        userController.doPost(httpRequest);

        User user = DataBase.findUserById("woowa");

        assertThat(user.getUserId()).isEqualTo("woowa");
        assertThat(user.getEmail()).isEqualTo("woo@w.a");
        assertThat(user.getName()).isEqualTo("woo");
        assertThat(user.getPassword()).isEqualTo("password");
    }
}