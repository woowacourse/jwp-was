package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HttpRequestTest {
    @Test
    void get() throws IOException {
        String request = "GET /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Upgrade-Insecure-Requests: 1\n";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest httpRequest = new HttpRequest(br);

        assertAll(
            () -> assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080"),
            () -> assertThat(httpRequest.getPath()).isEqualTo("/admin/"),
            () -> assertThat(httpRequest.getHttpMethod().name()).isEqualTo("GET")
        );
    }

    @Test
    void post() throws IOException {
        String request = "POST /admin/ HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Content-Length: 59\n"
            + "Connection: keep-alive\n"
            + "Upgrade-Insecure-Requests: 1\n\n"
            + "userId=javajigi&password=password&name=javajigi&email=javajigi@slipp.net";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest httpRequest = new HttpRequest(br);

        assertAll(
            () -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi"),
            () -> assertThat(httpRequest.getHttpMethod().name()).isEqualTo("POST")
        );
    }

    @Test
    public void post2() throws Exception {
        String request = "POST /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 46\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n\n"
            + "userId=javajigi&password=password&name=JaeSung";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);

        assertAll(
            () -> assertThat(httpRequest.getHttpMethod().name()).isEqualTo("POST"),
            () -> assertThat(httpRequest.getPath()).isEqualTo("/user/create"),
            () -> assertThat(httpRequest.getHeader("Connection")).isEqualTo("keep-alive"),
            () -> assertThat(httpRequest.getParameter("id")).isEqualTo("1"),
            () -> assertThat(httpRequest.getParameter("userId")).isEqualTo("javajigi")
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameterAndAnswer")
    void containsParameter(String parameter, boolean result) throws IOException {
        String request = "POST /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 46\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n\n"
            + "userId=javajigi&password=password&name=JaeSung";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);

        assertThat(httpRequest.containsParameter(parameter)).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("provideParametersAndAnswer")
    void containsAll(String[] parameters, boolean result) throws IOException {
        String request = "POST /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 46\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n\n"
            + "userId=javajigi&password=password&name=JaeSung";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);

        assertThat(httpRequest.containsAll(parameters)).isEqualTo(result);
    }

    @Test
    void extractSessionId() throws IOException {
        String request = "GET /user/create?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cookie: SESSIONID=aaa";
        InputStream in = new ByteArrayInputStream(request.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new HttpRequest(br);

        assertThat(httpRequest.getSessionId()).isEqualTo("aaa");
    }

    private static Stream<Arguments> provideParameterAndAnswer() {
        return Stream.of(
            Arguments.of("userId", true),
            Arguments.of("email", false)
        );
    }

    private static Stream<Arguments> provideParametersAndAnswer() {
        return Stream.of(
            Arguments.of(new String[] {"userId", "password", "name"}, true),
            Arguments.of(new String[] {"userId", "password", "name", "email"}, false)
        );
    }
}
