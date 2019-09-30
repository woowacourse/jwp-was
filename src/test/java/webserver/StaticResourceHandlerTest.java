package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import utils.TestResourceLoader;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.response.HttpResponse.CRLF;
import static org.assertj.core.api.Assertions.assertThat;
import static webserver.StaticResourceHandler.VIEW_TEMPLATE_PATH;

class StaticResourceHandlerTest {
    @Test
    void forward() throws IOException, URISyntaxException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Static.txt");
        HttpResponse response = HttpResponse.of(request.getVersion());
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath(VIEW_TEMPLATE_PATH + "/index.html");

        StaticResourceHandler.forward(request, response);

        assertThat(response.getMessageHeader()).isEqualTo("HTTP/1.1 200 OK" + CRLF
                + "Content-Type: text/html" + CRLF
                + "Content-Length: " + expectedBody.length + CRLF);
    }

    @Test
    void 정적_파일이_없는_경우_404응답() throws IOException, URISyntaxException {
        HttpRequest request = TestResourceLoader.getHttpRequest("Http_GET_Not_Exsisting_File.txt");
        HttpResponse response = HttpResponse.of(request.getVersion());

        StaticResourceHandler.forward(request, response);

        assertThat(response.getMessageHeader()).isEqualTo("HTTP/1.1 404 Not Found" + CRLF);
    }

    @Test
    void MediaType을_알_수_없는_경우_html로_응답() throws IOException, URISyntaxException {
        HttpRequest request = TestResourceLoader
                .getHttpRequest("Http_GET_Static_Without_Accept_Header.txt");
        HttpResponse response = HttpResponse.of(request.getVersion());
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath(VIEW_TEMPLATE_PATH + "/index.html");

        StaticResourceHandler.forward(request, response);

        assertThat(response.getMessageHeader()).isEqualTo("HTTP/1.1 200 OK" + CRLF
                + "Content-Type: text/html" + CRLF
                + "Content-Length: " + expectedBody.length + CRLF);

    }
}