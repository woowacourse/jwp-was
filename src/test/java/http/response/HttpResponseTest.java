package http.response;

import http.request.HttpRequest;
import http.request.RequestHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    private static final String TEST_DIRECTORY = "./src/test/resources/";

    private HttpResponse httpResponse;

    @BeforeEach
    public void setUp() {
        httpResponse = new ResponseHandler().create();
    }

    @Test
    public void putHeaderTest() {
        httpResponse.putHeader("Connection", "keep-alive");

        assertThat(httpResponse.toString()).contains("Connection: keep-alive");
    }

    @Test
    public void okTest() {
        String body = "HELLO!";
        httpResponse.ok(body.getBytes());

        assertThat(httpResponse.getBody()).isEqualTo(body.getBytes());
    }

    @Test
    public void redirectTest() {
        httpResponse.redirect("index.html");

        assertThat(httpResponse.toString()).contains("302 FOUND");
        assertThat(httpResponse.toString()).contains("Location: index.html");
    }

    @Test
    public void addHeaderFromRequest() throws IOException {
        InputStream in = new FileInputStream(new File(TEST_DIRECTORY + "Http_GET_File.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest httpRequest = new RequestHandler(br).create();
        httpResponse.addHeaderFromRequest(httpRequest);

        assertThat(httpResponse.toString()).contains("HTTP/1.1");
        assertThat(httpResponse.toString()).contains("Content-Type: text/html;charset=utf-8");
    }

    @Test
    public void sendNotFoundTest() {
        httpResponse.sendNotFound();

        assertThat(httpResponse.toString()).contains("404 NOT FOUND");
    }

    @Test
    public void sendMethodNotAllowedTest() {
        httpResponse.sendMethodNotAllowed();

        assertThat(httpResponse.toString()).contains("405 METHOD NOT ALLOWED");
    }
}