package webserver.controller;

import org.junit.jupiter.api.Test;
import webserver.WebTestForm;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class WelcomePageControllerTest extends WebTestForm {

    @Test
    void GET_정상처리() throws IOException {
        HttpRequest httpRequest = getHttpGetRequest("/");
        HttpResponse httpResponse = new HttpResponse();

        String view = WelcomePageController.getInstance().service(httpRequest, httpResponse);
        assertThat(view).isEqualTo("/index.html");
    }
}