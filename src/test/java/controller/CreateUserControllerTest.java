package controller;

import controller.exception.URINotFoundException;
import db.DataBase;
import http.HttpRequest;
import http.HttpRequestParser;
import http.HttpResponse;
import org.junit.jupiter.api.Test;
import view.View;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static http.HttpRequestTest.GET_REQUEST;
import static http.HttpRequestTest.POST_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateUserControllerTest {
    private CreateUserController createUserController = new CreateUserController();

    @Test
    void 유저_생성() throws UnsupportedEncodingException {
        int size = DataBase.findAll().size();
        List<String> postRequestLines = Arrays.asList(POST_REQUEST.split("\n"));
        HttpRequest request = HttpRequestParser.parse(postRequestLines);
        HttpResponse response = new HttpResponse();

        View view = createUserController.service(request, response);

        assertThat(view.isRedirectView()).isTrue();
        assertThat(view.getViewName()).isEqualTo("index.html");
        assertThat(DataBase.findAll()).hasSize(size + 1);
    }

    @Test
    void GET_메소드_에러() throws UnsupportedEncodingException {
        List<String> getRequestLines = Arrays.asList(GET_REQUEST.split("\n"));
        HttpRequest request = HttpRequestParser.parse(getRequestLines);
        HttpResponse response = new HttpResponse();

        assertThrows(URINotFoundException.class, () -> createUserController.service(request, response));
    }
}
