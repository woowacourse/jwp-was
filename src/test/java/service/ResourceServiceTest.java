package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Stream;
import model.general.Header;
import model.request.Request;
import model.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResourceServiceTest {

    @Test
    @DisplayName("Resource Service 동작 테스트")
    void execute() throws IOException, URISyntaxException {
        String filePath = "src/test/resources/input/get_template_file_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);
        Response response = ResourceService.execute(request);

        Map<Header, String> headers = response.getHeaders();
        byte[] body = response.getBody();

        Stream.of(
            assertThat(response).isInstanceOf(Response.class),
            assertThat(String.valueOf(body.length)).isEqualTo(headers.get(Header.CONTENT_LENGTH)),
            assertThat(headers.get(Header.CONTENT_TYPE)).isEqualTo("text/html"),
            assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(response.getStatusCode()).isEqualTo("200"),
            assertThat(response.getReasonPhrase()).isEqualTo("OK")
        );
    }
}
