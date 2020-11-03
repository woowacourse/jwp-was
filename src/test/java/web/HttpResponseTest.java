package web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.http.HttpResponse;
import web.http.HttpStatus;

public class HttpResponseTest {

    private String testDirectory = "./src/test/resources/";

    @DisplayName("GET - 200 응답")
    @Test
    public void responseForward() {
        HttpResponse response = new HttpResponse(null);
        response.ok("templates/index.html");

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("POST - 302 응답")
    @Test
    public void responseRedirect() {
        HttpResponse response = new HttpResponse(null);
        response.sendRedirect("/index.html");

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @DisplayName("add cookie - 302 응답")
    @Test
    public void responseCookies() {
        HttpResponse response = new HttpResponse(null);
        response.addHeader("Set-Cookie", "JSESSIONID=123123123");
        response.sendRedirect("/index.html");

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }
}
