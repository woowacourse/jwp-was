package http.response;

import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.HttpVersion.HTTP_1_1;
import static http.response.HttpStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class HttpResponseTest {
    @Test
    void httpResponse_생성() {
        assertThatCode(() -> HttpResponse.of(HTTP_1_1)).doesNotThrowAnyException();
    }

    @Test
    void httpResponse_forward() throws IOException, URISyntaxException {
        String directory = "./templates";
        String resourceName = "/index.html";
        HttpResponse response = HttpResponse.of(HTTP_1_1);
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath(directory + resourceName);

        response.setStatus(OK);
        response.forward(resourceName);

        assertThat(response.getMessageHeader()).isEqualTo("HTTP/1.1 200 OK\r\n");
        assertThat(response.getBody()).isEqualTo(expectedBody);
    }

    @Test
    void httpResponse_Redirect() {
        HttpResponse response = HttpResponse.of(HTTP_1_1);
        String redirected = "/index.html";

        response.sendRedirect(redirected);

        assertThat(response.getMessageHeader()).isEqualTo("HTTP/1.1 302 Found\r\n"
                + "Location: " + redirected + "\r\n");
    }

    @Test
    void hasBody() {
        HttpResponse response = HttpResponse.of(HTTP_1_1);
        byte[] body = "body".getBytes();

        response.setBody(body);

        assertThat(response.hasBody()).isTrue();
    }
}
