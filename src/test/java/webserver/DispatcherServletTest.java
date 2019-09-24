package webserver;

import http.*;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static http.HttpHeader.CONTENT_TYPE;
import static http.HttpRequestTest.POST_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

class DispatcherServletTest {
    @Test
    void static_파일_요청() throws IOException, URISyntaxException {
        String filePath = "/css/styles.css";
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine(filePath, HttpMethod.GET))
                .build();
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeader(CONTENT_TYPE)).isEqualTo(MimeType.of(filePath));
        assertThat(response.getBody()).isEqualTo(FileIoUtils.loadFileFromClasspath("./static" + filePath));
    }

    @Test
    void 존재하지않는_static_파일_요청() {
        HttpRequest request = new HttpRequest.HttpRequestBuilder()
                .startLine(new HttpStartLine("/css/styles.cs", HttpMethod.GET))
                .build();
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void 동적_URL_요청() throws UnsupportedEncodingException {
        String uri = "/user/create";
        List<String> postRequestLines = Arrays.asList(POST_REQUEST.split("\n"));
        HttpRequest request = HttpRequestParser.parse(postRequestLines);
        HttpResponse response = new HttpResponse();

        DispatcherServlet.doDispatch(request, response);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getHeader("Location")).isEqualTo("/index.html");
    }
}