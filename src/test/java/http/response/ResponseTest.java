package http.response;

import http.ContentType;
import http.request.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ResponseTest {
    @DisplayName("found 테스트")
    @Test
    void responseFound() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Response response = new Response(result);
        response.found("/index.html");

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 302 Found "),
                () -> assertThat(actual).contains("Location: /index.html ")
        );
    }

    @DisplayName("ok 테스트")
    @Test
    void responseOk() throws IOException, URISyntaxException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Response response = new Response(result);
        response.ok("/index.html", ContentType.HTML.getContentType());

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 200 OK "),
                () -> assertThat(actual).contains("Content-Type: text/html;charset=UTF-8 ")
        );
    }

    @DisplayName("methodNotAllowed 테스트")
    @Test
    void responseMethodNotAllowed() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Response response = new Response(result);
        response.methodNotAllowed(RequestMethod.POST);

        String actual = result.toString();

        assertAll(
                () -> assertThat(actual).contains("HTTP/1.1 405 Method Not Allowed "),
                () -> assertThat(actual).contains("Request method POST not supported")
        );
    }
}
