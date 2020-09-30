package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

import model.User;
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

    @Test
    public void requestTest_POST_params() throws Exception {
        Request request = generateRequest("http_POST_params.txt");
        Body body = request.getBody();
        User user = body.bodyMapper(User.class);

        assertThat(request.getMethod()).isEqualTo(POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getName()).isEqualTo("JaeSung");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }
}
