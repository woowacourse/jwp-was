package web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;
import web.HttpRequest;
import web.HttpResponse;
import webserver.RequestMapping;

class LoginControllerTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("로그인")
    @Test
    public void login() throws IOException {
        DataBase.addUser(new User("javajigi", "javable", "pobi", "test@test.com"));

        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST_LOGIN.txt"));
        HttpRequest request = HttpRequest.from(in);
        HttpResponse response = new HttpResponse(createOutputStream("LOGIN_USER.txt"));

        Controller controller = RequestMapping.getController("/user/login");
        controller.service(request, response);
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}