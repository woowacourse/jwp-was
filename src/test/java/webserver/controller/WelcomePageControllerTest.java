package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.View;
import webserver.WebTestForm;
import webserver.exception.NotSupportedHttpMethodException;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WelcomePageControllerTest extends WebTestForm {

    WelcomePageController welcomePageController = WelcomePageController.getInstance();

    @Test
    void GET_정상처리() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/");
        HttpResponse httpResponse = new HttpResponse();

        View view = welcomePageController.service(httpRequest, httpResponse);
        assertThat(view.getName()).isEqualTo("/index.html");
    }

    @Test
    void POST_요청_에러_처리() throws IOException {
        HttpRequest httpRequest = getHttpPostRequestWithBody("/");
        HttpResponse httpResponse = new HttpResponse();

        assertThrows(NotSupportedHttpMethodException.class, () -> welcomePageController.service(httpRequest, httpResponse));
    }
}