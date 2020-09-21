package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.Status.FOUND;
import static webserver.Status.OK;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import webserver.response.Response;

public class ResponseTest extends WebServerTest {

    @Test
    public void responseTest_GET() throws IOException {
        Response response = generateResponse(generateRequest("http_GET_index.txt"));

        assertThat(response.getStatus()).isEqualTo(OK);
        assertThat(response.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeaders().get("Content-Type")).contains("text/html");
        assertThat(response.getBody()).hasSizeGreaterThan(1);
    }

    @Test
    public void responseTest_POST() throws IOException {
        Response response = generateResponse(generateRequest("http_POST.txt"));

        assertThat(response.getStatus()).isEqualTo(FOUND);
        assertThat(response.getVersion()).isEqualTo("HTTP/1.1");
        assertThat(response.getHeaders().get("Location")).isEqualTo("http://localhost:8080/index.html ");
    }
}
