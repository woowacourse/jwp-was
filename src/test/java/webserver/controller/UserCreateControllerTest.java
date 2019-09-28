package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.WebTestForm;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserCreateControllerTest extends WebTestForm {

    UserCreateController userCreateController = UserCreateController.getInstance();

    @Test
    void 회원가입_GET_요청_에러_처리() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/user/create");
        HttpResponse httpResponse = new HttpResponse();

        assertThrows(NotSupportedHttpMethodException.class, () -> userCreateController.service(httpRequest, httpResponse));
    }

    @Test
    void 회원가입_성공_출력_테스트() throws IOException {
        HttpRequest httpRequest = getHttpPostRequestWithBody("/user/create");
        HttpResponse httpResponse = new HttpResponse();

        String view = userCreateController.service(httpRequest, httpResponse);
        assertThat(view).isEqualTo("/redirect:/index.html");
    }
}