package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionManagerTest {
    @Test
    void getSessionTest() {
        HttpSessionManager sessionManager = new HttpSessionManager();
        HttpSession httpSession = sessionManager.getSession("JSESSIONID");
        assertThat(httpSession).isNull();
    }

}