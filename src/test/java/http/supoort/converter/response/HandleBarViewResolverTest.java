package http.supoort.converter.response;

import http.model.response.ServletResponse;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HandleBarViewResolverTest {
    private ViewResolver viewResolver = new HandleBarViewResolver();
    private List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        users.add(new User("andole", "password", "andole", "andole@andole.com"));
        users.add(new User("coogi", "password", "coogi", "coogi@coogi.com"));
    }

    @Test
    void 핸들바_렌더링() {
        ServletResponse servletResponse = new ServletResponse(new ByteArrayOutputStream());
        DataOutputStream outputStream = new DataOutputStream(new ByteArrayOutputStream());

        servletResponse.ok("/user/list", new HashMap<String, Object>() {{
            put("users", users);
        }});

        int size = outputStream.size();

        viewResolver.render(servletResponse, outputStream);

        assertThat(outputStream.size()).isNotEqualTo(size);
    }
}