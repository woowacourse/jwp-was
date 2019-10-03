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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicControllerTest {
    static final String TEST_RESOURCES = "./src/test/resources";
    private final String BASIC_CONTROLLER_DIR = "/controller/basic";

    private Controller controller;
    private InputStream in;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        controller = new BasicController();
        httpResponse = new HttpResponse();
    }

    @Test
    @DisplayName("/index.html 요청시 200 OK와 함께 Content-Type이 text/html인 response가 나타나는지")
    void html파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + BASIC_CONTROLLER_DIR + "/html_get.txt");
        httpRequest = HttpRequestParser.parse(in);
        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.HTML.getContentType());
    }

    @Test
    @DisplayName("/css/styles.css 요청시 200 OK와 함께 Content-Type이 text/css인 response가 나타나는지")
    void css파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + BASIC_CONTROLLER_DIR + "/css_get.txt");
        httpRequest = HttpRequestParser.parse(in);

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.CSS.getContentType());
    }

    @Test
    @DisplayName("/js/scripts.js 요청시 200 OK와 함께 Content-Type이 text/javascript인 response가 나타나는지")
    void js파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + BASIC_CONTROLLER_DIR + "/js_get.txt");
        httpRequest = HttpRequestParser.parse(in);

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.JS.getContentType());
    }

    @Test
    @DisplayName("/fonts/glyphicons-halflings-regular.svg 요청시 200 OK와 함께 Content-Type이 application/*; image/*인 response가 나타나는지")
    void font파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + BASIC_CONTROLLER_DIR + "/font_get.txt");
        httpRequest = HttpRequestParser.parse(in);

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.FONT.getContentType());
    }

    @Test
    @DisplayName("/images/80-text.png 요청시 200 OK와 함께 Content-Type이 image/*인 response가 나타나는지")
    void images파일_요청_정상_흐름_테스트() throws IOException {
        in = new FileInputStream(TEST_RESOURCES + BASIC_CONTROLLER_DIR + "/image_get.txt");
        httpRequest = HttpRequestParser.parse(in);

        controller.service(httpRequest, httpResponse);

        StatusLine statusLine = httpResponse.getStatusLine();
        assertThat(statusLine.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(statusLine.getHttpVersion()).isEqualTo(HttpVersion.HTTP_1_1);

        assertThat(httpResponse.getHttpHeader().get("Content-Type")).isEqualTo(HttpContentType.IMG.getContentType());
    }
}
