package view;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import web.StaticFile;

public class ViewTest {

    @DisplayName("동적으로 페이지 랜더링")
    @Test
    void renderTest() throws IOException {
        View view = new View(StaticFile.of("/user/list.html").getPrefix() + "/user/list.html");

        User user = new User("javajigi", "p123", "pobi", "javajigi@gmail.com");

        List<User> users = Lists.newArrayList(user);

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String render = view.apply(model);
        assertThat(render).contains(user.getUserId());
        assertThat(render).contains(user.getName());
        assertThat(render).contains(user.getEmail());
    }
}
