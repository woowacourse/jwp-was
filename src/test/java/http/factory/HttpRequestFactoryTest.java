package http.factory;

import http.request.HttpMethod;
import http.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestFactoryTest {
    private String testDirectory = "./src/test/resources/";

    @DisplayName("Request를 만드는 메서드 테스트 - get방식")
    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest httpRequest = HttpRequestFactory.createRequest(br);
        assertAll(
                () -> assertEquals(HttpMethod.GET, httpRequest.getMethod()),
                () -> assertEquals("/user/create", httpRequest.getPath()),
                () -> assertEquals("keep-alive", httpRequest.getHeader("Connection")),
                () -> assertEquals("javajigi", httpRequest.getParameter("userId"))
        );
    }

    @DisplayName("Request를 만드는 메서드 테스트 - post방식")
    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest httpRequest = HttpRequestFactory.createRequest(br);

        assertAll(
                () -> assertEquals(HttpMethod.POST, httpRequest.getMethod()),
                () -> assertEquals("/user/create", httpRequest.getPath()),
                () -> assertEquals("keep-alive", httpRequest.getHeader("Connection")),
                () -> assertEquals("javajigi", httpRequest.getParameter("userId"))
        );
    }

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_Post_With_Query_String"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest httpRequest = HttpRequestFactory.createRequest(br);

        assertAll(
                () -> assertEquals(HttpMethod.POST, httpRequest.getMethod()),
                () -> assertEquals("/user/create", httpRequest.getPath()),
                () -> assertEquals("keep-alive", httpRequest.getHeader("Connection")),
                () -> assertEquals("1", httpRequest.getParameter("id")),
                () -> assertEquals("javajigi", httpRequest.getParameter("userId")),
                () -> assertEquals("JaeSung", httpRequest.getParameter("name"))
        );
    }
}
