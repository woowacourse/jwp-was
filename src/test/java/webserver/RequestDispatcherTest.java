package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestDispatcherTest {

    @Test
    @DisplayName("static 파일 요청 성공 응답 성공")
    void static_file() {
        Request req = new Request(HttpMethod.GET, "/css/styles.css", new HashMap<>(), null, null, null);
        Response res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("index.html 요청 응답 성공")
    void index() {
        Request req = new Request(HttpMethod.GET, "/index.html", new HashMap<>(), null, null, null);
        Response res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("잘못된 파일 요청 응답 실패")
    void not_found() {
        Request req = new Request(HttpMethod.GET, "index.hhtml", new HashMap<>(), null, null, null);
        Response res = RequestDispatcher.handle(req);
        assertThat(res.getStatus().getCode()).isEqualTo(404);
    }
}
