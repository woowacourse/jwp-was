package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;
import model.general.Header;
import model.request.Request;
import model.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ApiServiceTest {

    @Test
    @DisplayName("API Service 동작 테스트")
    void execute() throws IOException {
        String filePath = "src/test/resources/input/post_api_request.txt";
        InputStream inputStream = new FileInputStream(filePath);
        Request request = Request.of(inputStream);

        Response response = ApiService.execute(request);
        Map<Header, String> headers = response.getHeaders();

        Stream.of(
            assertThat(response).isInstanceOf(Response.class),
            assertThat(response.getBody()).isEqualTo(null),
            assertThat(headers.get(Header.LOCATION)).isEqualTo("/index.html"),
            assertThat(response.getHttpVersion()).isEqualTo("HTTP/1.1"),
            assertThat(response.getStatusCode()).isEqualTo("302"),
            assertThat(response.getReasonPhrase()).isEqualTo("Found")
        );
    }
}
