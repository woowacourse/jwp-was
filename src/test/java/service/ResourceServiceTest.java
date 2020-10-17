package service;

import static org.assertj.core.api.Assertions.assertThat;

import controller.Controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;
import model.general.Header;
import model.request.HttpRequest;
import model.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ControllerMapper;

public class ResourceServiceTest {

    @Test
    @DisplayName("Resource Service 동작 테스트")
    void execute() throws IOException {
        String filePath = "src/test/resources/input/get_template_file_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        HttpRequest httpRequest = HttpRequest.of(inputStream);
        Controller controller = ControllerMapper.selectController(httpRequest);
        HttpResponse httpResponse = controller.service(httpRequest);

        Map<Header, String> headers = httpResponse.getHeaders();
        byte[] body = httpResponse.getBody();

        Stream.of(
            assertThat(httpResponse).isInstanceOf(HttpResponse.class),
            assertThat(String.valueOf(body.length)).isEqualTo(headers.get(Header.CONTENT_LENGTH)),
            assertThat(headers.get(Header.CONTENT_TYPE)).isEqualTo("text/html"),
            assertThat(httpResponse.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(httpResponse.getStatusCode()).isEqualTo("200"),
            assertThat(httpResponse.getReasonPhrase()).isEqualTo("OK")
        );
    }
}
