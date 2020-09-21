package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

import org.junit.jupiter.api.Test;
import webserver.request.Request;

public class RequestTest extends WebServerTest {

    @Test
    public void requestTest_GET() throws Exception {
        Request request = generateRequest("http_GET_index.txt");

        assertThat(request.getMethod()).isEqualTo(GET);
        assertThat(request.getPath()).isEqualTo("/index.html");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    public void requestTest_POST() throws Exception {
        Request request = generateRequest("http_POST.txt");

        assertThat(request.getMethod()).isEqualTo(POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }
}
