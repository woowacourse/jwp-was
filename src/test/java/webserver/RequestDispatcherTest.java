package webserver;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestDispatcherTest {

    @Test
    void static_file() {
        String uri = "/css/styles.css";
        HttpRequest req = new HttpRequest(HttpMethod.GET, uri, uri, new HashMap<>(), null, null);
        HttpResponse res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(200);
    }

    @Test
    void index() {
        String uri = "/index.html";
        HttpRequest req = new HttpRequest(HttpMethod.GET, uri, uri, new HashMap<>(), null, null);
        HttpResponse res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(200);
    }

    @Test
    void not_found() {
        String uri = "index.hhtml";
        HttpRequest req = new HttpRequest(HttpMethod.GET, uri, uri, new HashMap<>(), null, null);
        HttpResponse res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(404);
    }
}
