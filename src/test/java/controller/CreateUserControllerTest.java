package controller;

import controller.exception.URINotFoundException;
import db.DataBase;
import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import org.junit.jupiter.api.Test;
import view.View;

import java.io.IOException;

import static http.HttpRequestTest.GET_REQUEST;
import static http.HttpRequestTest.POST_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.IOUtils.convertStringToInputStream;

public class CreateUserControllerTest {
    private CreateUserController createUserController = new CreateUserController();

    @Test
    void 유저_생성() throws IOException {
        int size = DataBase.findAll().size();
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(POST_REQUEST));
        HttpResponse response = new HttpResponse();

        View view = createUserController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("index.html");
        assertThat(DataBase.findAll()).hasSize(size + 1);
    }

    @Test
    void GET_메소드_에러() throws IOException {
        HttpRequest request = HttpRequestParser.parse(convertStringToInputStream(GET_REQUEST));
        HttpResponse response = new HttpResponse();

        assertThrows(URINotFoundException.class, () -> createUserController.service(request, response));
    }
}
