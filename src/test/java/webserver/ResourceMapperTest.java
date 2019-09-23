package webserver;

import http.HttpHeaders;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponseEntity;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import static http.response.HttpStatus.NOT_FOUND;
import static http.response.HttpStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;

class ResourceMapperTest {
    @Test
    void Request_URI에_해당하는_리소스가_Templates에_있는_경우() throws IOException, URISyntaxException {
        String requestMessage = "GET /index.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);

        HttpResponseEntity responseEntity = ResourceMapper.map(request);

        assertThat(responseEntity.getStatus()).isEqualTo(OK);
        assertThat(responseEntity.getHeaders()).isEqualTo(new HttpHeaders());
        assertThat(responseEntity.getViewTemplatePath()).isEqualTo("./templates/index.html");
    }

    @Test
    void Request_URI에_해당하는_리소스가_Static에_있는_경우() throws IOException, URISyntaxException {
        String requestMessage = "GET /css/styles.css HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);

        HttpResponseEntity responseEntity = ResourceMapper.map(request);

        assertThat(responseEntity.getStatus()).isEqualTo(OK);
        assertThat(responseEntity.getHeaders()).isEqualTo(new HttpHeaders());
        assertThat(responseEntity.getViewTemplatePath()).isEqualTo("./static/css/styles.css");
    }

    @Test
    void Request_URI에_해당하는_리소스가_없는_경우() throws IOException, URISyntaxException {
        String requestMessage = "GET /not_exist.html HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*";
        InputStream in = new ByteArrayInputStream(requestMessage.getBytes());
        HttpRequest request = HttpRequestFactory.makeHttpRequest(in);

        HttpResponseEntity responseEntity = ResourceMapper.map(request);

        assertThat(responseEntity.getStatus()).isEqualTo(NOT_FOUND);
        assertThat(responseEntity.getHeaders()).isEqualTo(new HttpHeaders());
        assertThat(responseEntity.getViewTemplatePath()).isEqualTo("./templates/error/404.html");
    }
}