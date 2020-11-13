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
        InputStream in = new FileInputStream(new File(testDirectory + "get.txt"));
        HttpRequest httpRequest = new HttpRequest(in);

        assertAll(() -> {
            assertTrue(httpRequest.matchMethod(HttpMethod.GET));
            assertEquals("keep-alive", httpRequest.getHeader("Connection"));
            assertEquals("orange", httpRequest.getParam("userId"));
            assertEquals("Yerin", httpRequest.getParam("name"));
            assertFalse(httpRequest.isBodyExist());
        });
    }

    @Test
    @DisplayName("POST 요청에 대한 테스트")
    void postRequestTest() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "post.txt"));
        HttpRequest httpRequest = new HttpRequest(in);

        assertAll(() -> {
            assertTrue(httpRequest.matchMethod(HttpMethod.POST));
            assertEquals("keep-alive", httpRequest.getHeader("Connection"));
            assertEquals("46", httpRequest.getHeader("Content-Length"));
            assertEquals("application/x-www-form-urlencoded", httpRequest.getHeader("Content-Type"));
            assertEquals("orange", httpRequest.getParam("userId"));
            assertEquals("Yerin", httpRequest.getParam("name"));
            assertTrue(httpRequest.isBodyExist());
        });
    }

    @Test
    @DisplayName("서버에서 지원하지 않는 http 요청(get, post, put, patch, delete 제외)에 대한 테스트")
    void notSupportedMethod() throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + "connect_not_allowed.txt"));
        HttpRequest httpRequest = new HttpRequest(in);

        assertTrue(httpRequest.matchMethod(HttpMethod.NOT_SUPPORT_METHOD));
    }
}
