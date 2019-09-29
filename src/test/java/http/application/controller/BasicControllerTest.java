package http.application.controller;

import http.application.Controller;
import http.common.HttpVersion;
import http.request.HttpRequest;
import http.request.HttpRequestParser;
import http.response.HttpContentType;
import http.response.HttpResponse;
import http.response.HttpStatus;
import http.response.StatusLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicControllerTest {
    private static final String TEST_RESOURCES = "./src/test/resources";

    Controller controller;
    InputStream in;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        controller = new BasicController();
    }

    @Test
    void html파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + "/html_get.txt");
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.HTML.getContentType());
    }

    @Test
    void css파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + "/css_get.txt");
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.CSS.getContentType());

    }

    @Test
    void js파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + "/js_get.txt");
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.JS.getContentType());
    }

    @Test
    void font파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + "/font_get.txt");
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.FONT.getContentType());
    }

    @Test
    void images파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + "/image_get.txt");
        HttpRequest httpRequest = HttpRequestParser.parse(in);
        HttpResponse httpResponse = new HttpResponse();

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.IMG.getContentType());
    }
}
