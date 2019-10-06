package webserver.cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.controller.BasicTests;
import webserver.controller.cookie.HttpCookie;
import webserver.controller.session.UUIDGenerator;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static webserver.controller.cookie.HttpCookie.FALSE;

public class HttpCookieTests extends BasicTests {

    @Test
    void create_firstRequest() {
        UUID uuid = UUIDGenerator.generateUUID();
        HttpCookie httpCookie = new HttpCookie(uuid);
        Map<String, String> fields = httpCookie.getFields();

        assertThat(httpCookie.getSessionId()).isEqualTo(uuid.toString());
        assertThat(fields.get("logined")).isEqualTo(FALSE);
        assertThat(fields.get("sessionid")).isEqualTo(uuid.toString());
    }

    @Test
    void non_firstRequest() {

    }
}
