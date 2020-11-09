package interfaces;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import web.controller.Controller;
import web.http.HttpRequest;
import web.http.HttpResponse;
import web.http.HttpStatus;

class CreateUserControllerTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("회원 가입")
    @Test
    public void createUser() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_Create.txt"));
        HttpRequest request = new HttpRequest(in);
        HttpResponse response = new HttpResponse(null);

        Controller createUserController = new CreateUserController();
        createUserController.service(request, response);

        assertThat(DataBase.findAll()).hasSize(1);
        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}