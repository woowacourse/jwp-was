package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.WebTestForm;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateControllerTest extends WebTestForm {

    @Test
    void 회원가입_성공_출력_테스트() throws IOException {
        HttpRequest httpRequest = getHttpPostRequest("/user/create");
        HttpResponse httpResponse = new HttpResponse();

        String view = UserCreateController.getInstance().service(httpRequest, httpResponse);
        assertThat(view).isEqualTo("/redirect:/index.html");
    }
}