package webserver.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionHelperTest {

    @Test
    void create() {
        HttpSessionHelper.clear();
        HttpSession userSession = new HttpSession();
        userSession.setAttribute("userId", "id");
        String userSessionId = HttpSessionHelper.create(userSession);

        HttpSession userSession2 = HttpSessionHelper.get(userSessionId);

        assertThat(userSession).isEqualTo(userSession2);
        assertThat(userSession2.getAttribute("userId")).isEqualTo("id");
        HttpSessionHelper.clear();
        assertThat(HttpSessionHelper.get(userSessionId)).isEqualTo(null);
    }
}