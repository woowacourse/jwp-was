package webserver.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.model.User;
import webserver.message.response.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HandleBarViewTest {

    @Test
    @DisplayName("HandleBarView가 렌더링을 잘 하는지 테스트")
    void handleBarRender() {
        Response response = new Response();
        View view = new HandleBarView("/user/list");
        User user1 = new User("van", "vsh123", "반선호씨", "vsh123@gmail.com");
        User user2 = new User("hj", "password123", "박쪼다씨", "hgenie14@gmail.com");
        List<User> users = Arrays.asList(user1, user2);

        Map<String, Object> models = new HashMap<>();
        models.put("users", users);

        view.render(response, models);

        String actual = new String(response.toBytes());
        assertThat(actual).contains("vsh123@gmail.com");
        assertThat(actual).contains("hgenie14@gmail.com");
    }
}