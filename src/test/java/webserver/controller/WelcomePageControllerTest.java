package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.View;
import webserver.WebTestForm;
import webserver.http.HttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class WelcomePageControllerTest extends WebTestForm {

    WelcomePageController welcomePageController = new WelcomePageController();

    @Test
    void GET_정상처리() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/");

        View view = welcomePageController.service(httpRequest);
        assertThat(view.getName()).isEqualTo("/index.html");
    }

    @Test
    void POST_요청_에러_처리() throws IOException {
        HttpRequest httpRequest = getHttpPostRequestWithBody("/");
        View view = welcomePageController.service(httpRequest);
        assertThat(view.getName()).isEqualTo("/error:METHOD_NOT_ALLOWED");
    }
}