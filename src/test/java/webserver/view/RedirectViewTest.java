package webserver.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.message.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectViewTest {

    @Test
    @DisplayName("Redirect 헤더를 잘 생성하는지 테스트")
    void redirectHeader() {
        Response response = new Response();
        View view = new RedirectView("/index.html");
        Map<String, Object> models = new HashMap<>();

        view.render(response, models);

        String actual = new String(response.toBytes());
        assertThat(actual).contains("302 Found");
        assertThat(actual).contains("Location: /index.html");
    }
}