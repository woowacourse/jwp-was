package view;

import model.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.staticfile.StaticFileMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ViewTest {

    @DisplayName("동적으로 페이지를 랜더링한다.")
    @Test
    void renderTest() throws IOException {
        View view = new View(StaticFileMatcher.findStaticFilePath("/user/list.html"));

        UserDto user1 = new UserDto("javajigi", "name", "javajigi@gmail.com");
        UserDto user2 = new UserDto("javajigi2", "name", "javajigi@gmail.com");

        List<UserDto> users = Arrays.asList(user1, user2);

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        String render = view.render(model);
        assertThat(render).contains(user1.getUserId());
        assertThat(render).contains(user1.getName());
        assertThat(render).contains(user2.getUserId());
        assertThat(render).contains(user2.getName());
    }
}