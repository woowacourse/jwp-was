package view;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandlebarsViewRendererTest {
    @Test
    public void getBodyTest() throws IOException {
        DataBase.addUser(new User("cony", "password", "cony", "cony@cony.com"));
        Map<String, Object> model = new HashMap<>();
        model.put("users", DataBase.findAll());

        assertThat(new String(HandlebarsViewRenderer.getBody("user/list.html", model), StandardCharsets.UTF_8))
                .contains("cony@cony.com");
    }
}