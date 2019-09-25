package dev.luffy.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @DisplayName("HttpRequest 의 GET 요청 처리가 가능한지 확인한다.")
    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpRequestMethod.GET, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getQueryParameter("userId"));
    }

    @DisplayName("HttpRequest 의 POST 요청 처리가 가능한지 확인한다.")
    @Test
    public void request_POST() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_POST.txt"));
        HttpRequest request = new HttpRequest(in);

        assertEquals(HttpRequestMethod.POST, request.getMethod());
        assertEquals("/user/create", request.getPath());
        assertEquals("keep-alive", request.getHeader("Connection"));
        assertEquals("javajigi", request.getBodyParameter("userId"));
    }
}