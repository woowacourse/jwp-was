package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestControllerTest {

    @Test
    void static_file() {
        Request req = new Request("GET", "/css/styles.css", null, null);
        Response res = RequestController.handle(req);
        assertThat(res.getStatusCode()).isEqualTo(200);
    }

    @Test
    void index() {
        Request req = new Request("GET", "/index.html", null, null);
        Response res = RequestController.handle(req);
        assertThat(res.getStatusCode()).isEqualTo(200);
    }

    @Test
    void not_found() {
        Request req = new Request("GET", "index.hhtml", null, null);
        Response res = RequestController.handle(req);
        assertThat(res.getStatusCode()).isEqualTo(404);
    }
}
