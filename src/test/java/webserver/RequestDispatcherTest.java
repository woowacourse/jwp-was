package webserver;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestDispatcherTest {

    @Test
    void static_file() {
        Request req = new Request("GET", "/css/styles.css", new HashMap<>(), null, null);
        Response res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(200);
    }

    @Test
    void index() {
        Request req = new Request("GET", "/index.html", new HashMap<>(), null, null);
        Response res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(200);
    }

    @Test
    void not_found() {
        Request req = new Request("GET", "index.hhtml", new HashMap<>(), null, null);
        Response res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(404);
    }
}
