package web.controller;

import static org.assertj.core.api.Assertions.*;

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
import web.http.HttpRequest;
import web.http.HttpResponse;
import webserver.RequestMapping;

class CreateUserControllerTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("회원 가입")
    @Test
    public void createUser() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = HttpRequest.from(in);
        HttpResponse response = new HttpResponse(createOutputStream("CREATE_USER.txt"));

        Controller controller = RequestMapping.getController("/user/create");
        controller.service(request, response);

        assertThat(DataBase.findAll()).hasSize(1);
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }

}