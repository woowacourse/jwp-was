package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.staticfile.StaticFileMatcher;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {

    @DisplayName("forward 테스트")
    @Test
    void forwardTest() throws IOException, URISyntaxException {
        String filePath = StaticFileMatcher.findStaticFilePath("/index.html");
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath(filePath);

        HttpResponse httpResponse = new HttpResponse(null);
        httpResponse.ok(filePath);

        assertThat(httpResponse.getBody()).isEqualTo(expectedBody);
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @DisplayName("redirect 테스트")
    @Test
    void redirectTest() throws IOException, URISyntaxException {
        HttpResponse httpResponse = new HttpResponse(null);
        httpResponse.redirect("/index.html");

        assertThat(httpResponse.getHeaders().get("Location")).isEqualTo("http://localhost:8080/index.html");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
    }

    @DisplayName("에러 발생 테스트")
    @Test
    void exceptionTest() {
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        byte[] expectedBody = "예외가 발생했습니다".getBytes();

        HttpResponse httpResponse = new HttpResponse(null);
        httpResponse.exception(expectedStatus, expectedBody);

        assertThat(httpResponse.getBody()).isEqualTo(expectedBody);
        assertThat(httpResponse.getHttpStatus()).isEqualTo(expectedStatus);
    }
}
