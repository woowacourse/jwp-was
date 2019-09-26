package webserver.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionHelperTest {

    @Test
    void create() {
        HttpSessionHelper.clear();
        HttpSession userSession = HttpSessionHelper.create("user_session");
        userSession.setAttribute("userId", "id");

        HttpSession userSession2 = HttpSessionHelper.get("user_session");

        assertThat(userSession).isEqualTo(userSession2);
        assertThat(userSession2.getAttribute("userId")).isEqualTo("id");
        HttpSessionHelper.clear();
        assertThat(HttpSessionHelper.get("user_session")).isEqualTo(null);
    }
}