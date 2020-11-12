package http;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {
    String testDirectory = "./src/test/resources/";

    @Test
    @DisplayName("GET 요청에 대한 테스트")
    void getRequestTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "GET.txt"));
        HttpRequest httpRequest = new HttpRequest(in);

        assertAll(() -> {
            assertTrue(httpRequest.isMethod(HttpMethod.GET));
            assertEquals("/user/create", httpRequest.getUrl());
            assertEquals("keep-alive", httpRequest.getHeader("Connection"));
            assertEquals("orange", httpRequest.getParam("userId"));
            assertEquals("Yerin", httpRequest.getParam("name"));
            assertFalse(httpRequest.isBodyExist());
        });
    }

    @Test
    @DisplayName("POST 요청에 대한 테스트")
    void postRequestTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "POST.txt"));
        HttpRequest httpRequest = new HttpRequest(in);

        assertAll(() -> {
            assertTrue(httpRequest.isMethod(HttpMethod.POST));
            assertEquals("/user/create", httpRequest.getUrl());
            assertEquals("keep-alive", httpRequest.getHeader("Connection"));
            assertEquals("46", httpRequest.getHeader("Content-Length"));
            assertEquals("application/x-www-form-urlencoded", httpRequest.getHeader("Content-Type"));
            assertEquals("orange", httpRequest.getParam("userId"));
            assertEquals("Yerin", httpRequest.getParam("name"));
            assertTrue(httpRequest.isBodyExist());
        });
    }

}
