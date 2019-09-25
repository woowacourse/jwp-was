package webserver;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestDispatcherTest {

    @Test
    void static_file() {
        String uri = "/css/styles.css";
        HttpRequest req = new HttpRequest(HttpMethod.GET, uri, uri, new HashMap<>(), new HashMap<>(),
            new HashMap<>(), Collections.emptyMap());
        HttpResponse res = new HttpResponse();
        RequestDispatcher.handle(req, res);
        assertThat(res.getStatus().getCode()).isEqualTo(HttpStatus.OK.getCode());
    }

    @Test
    void index() {
        String uri = "/index.html";
        HttpRequest req = new HttpRequest(HttpMethod.GET, uri, uri, new HashMap<>(), new HashMap<>(),
            new HashMap<>(), Collections.emptyMap());
        HttpResponse res = new HttpResponse();
        RequestDispatcher.handle(req, res);
        assertThat(res.getStatus().getCode()).isEqualTo(HttpStatus.OK.getCode());
    }

    @Test
    void not_found() {
        String uri = "index.hhtml";
        HttpRequest req = new HttpRequest(HttpMethod.GET, uri, uri, new HashMap<>(), new HashMap<>(),
            new HashMap<>(), Collections.emptyMap());
        HttpResponse res = new HttpResponse();
        RequestDispatcher.handle(req, res);
        assertThat(res.getStatus().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }
}
