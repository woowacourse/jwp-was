package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.View;
import webserver.WebTestForm;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertThrows(NotSupportedHttpMethodException.class, () -> welcomePageController.service(httpRequest));
    }
}