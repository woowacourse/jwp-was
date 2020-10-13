package web.response;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class HttpResponseTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponseTest.class);
    private ByteArrayOutputStream outputStream;

    @Test
    @DisplayName("200 OK 상태가 올바르게 반환된다")
    void createOkResponseTest() {
        try {
            outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            response.ok("/index.html", "text/html");
            String result = outputStream.toString();

            Assertions.assertThat(result).contains("HTTP/1.1 200 OK");
            Assertions.assertThat(result).contains("Content-Type: text/html;charset=UTF-8");

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @Test
    @DisplayName("302 Found 상태가 올바르게 반환된다")
    void createFoundTest() {
        outputStream = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(outputStream);

        response.found("/index.html");
        String result = outputStream.toString();

        Assertions.assertThat(result).contains("HTTP/1.1 302 FOUND");
        Assertions.assertThat(result).contains("Location: /index.html");
    }

    @Test
    @DisplayName("400 BAD REQUEST 상태가 올바르게 반환된다")
    void createBadRequestTest() {
        outputStream = new ByteArrayOutputStream();
        HttpResponse response = new HttpResponse(outputStream);

        response.badRequest("존재하지 않는 페이지입니다.");
        String result = outputStream.toString();

        Assertions.assertThat(result).contains("HTTP/1.1 400 BAD REQUEST");
        Assertions.assertThat(result).contains("errorMessage : 존재하지 않는 페이지입니다.");
    }
}
