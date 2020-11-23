package controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import request.HttpRequest;
import response.HttpResponse;
import session.Session;
import session.SessionStorage;

class StaticFileControllerTest {

    private StaticFileController staticFileController = new StaticFileController();

    @ParameterizedTest
    @ValueSource(strings = {"/", "/user/form.html", "/user/login.html", "/user/login_failed.html",
        "/user/profile.html", "/css/styles.css", "/fonts/glyphicons-halflings-regular.ttf"})
    @DisplayName("존재하는 파일 경로로 GET 방식 요청")
    void service_IfRequestUsesGetMethodAndExistingFilePath(String uri) {
        HttpRequest joinRequest = new HttpRequest(
            "GET " + uri + " HTTP/1.1\n", "");
        Session session = SessionStorage.createSession();

        HttpResponse response = staticFileController.service(joinRequest, session);
        String responseHeader = response.buildHeader();
        byte[] body = response.getBody();

        assertThat(responseHeader.startsWith("HTTP/1.1 200 OK")).isTrue();
        assertThat(body).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 파일 경로로 GET 방식 요청시 404 응답")
    void service_IfRequestUsesGetMethodAndNotExistingFilePath_respondWith404() {
        HttpRequest joinRequest = new HttpRequest(
            "GET /ohOhOhOh HTTP/1.1\n", "");
        Session session = SessionStorage.createSession();

        HttpResponse response = staticFileController.service(joinRequest, session);
        String responseHeader = response.buildHeader();
        byte[] body = response.getBody();

        assertThat(responseHeader.startsWith("HTTP/1.1 404 NotFound")).isTrue();
        assertThat(body).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"POST", "PUT", "DELETE"})
    @DisplayName("GET 방식이 아닌 요청시 404 응답")
    void service_IfRequestDoesNotUseGetMethod_respondWith404(String httpMethodNotGet) {
        HttpRequest joinRequest = new HttpRequest(
            httpMethodNotGet + " / HTTP/1.1\n", "");
        Session session = SessionStorage.createSession();

        HttpResponse response = staticFileController.service(joinRequest, session);
        String responseHeader = response.buildHeader();
        byte[] body = response.getBody();

        assertThat(responseHeader.startsWith("HTTP/1.1 404 NotFound")).isTrue();
        assertThat(body).isEmpty();
    }
}
