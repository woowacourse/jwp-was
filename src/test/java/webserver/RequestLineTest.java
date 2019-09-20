package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {

    @Test
    void create() {
        RequestLine rl = RequestLine.from("POST /user/create?email=john%40example.com HTTP/1.1\r\n");
        assertThat(rl.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(rl.getRequestUri()).isEqualTo("/user/create?email=john%40example.com");
        assertThat(rl.getPath()).isEqualTo("/user/create");
        assertThat(rl.getQueries().get("email")).isEqualTo("john@example.com");
    }
}
