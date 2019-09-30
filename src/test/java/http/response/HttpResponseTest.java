package http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        httpResponse = new HttpResponse();
    }

    @Test
    void ok() {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        httpResponse.forward(new ResponseBody(body));

        assertThat(httpResponse.getHttpStatus()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(httpResponse.getHeader("Content-Length")).isEqualTo(Integer.toString(body.length));
        assertThat(httpResponse.getResponseBody().getBody()).isEqualTo(body);
    }

    @Test
    void notFound() {
        httpResponse.error();

        assertThat(httpResponse.getHttpStatus()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void redirect() {
        String path = "/user/login_failed.html";
        httpResponse.redirect(path);

        assertThat(httpResponse.getHttpStatus()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(httpResponse.getHeader("Location")).isEqualTo(path);
    }
}