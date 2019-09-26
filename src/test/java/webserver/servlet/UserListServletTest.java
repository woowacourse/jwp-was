package webserver.servlet;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserListServletTest {
    @Test
    void handler_test() throws IOException {
        DataBase.deleteAll();
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        handlebars.registerHelper("inc", (Helper<Integer>) (context, options) -> context + 1);

        Template template = handlebars.compile("user/list");

        User user1 = new User("heebongId1", "password", "heebong1", "javajigi@gmail.com");
        User user2 = new User("heebongId2", "password", "희봉", "javajigi@gmail.com");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
        Map<String, Object> users = new HashMap<>();
        users.put("users", DataBase.findAll());
        String profilePage = template.apply(users);
        System.out.println(profilePage);
        assertThat(profilePage.contains("heebongId1")).isTrue();
        assertThat(profilePage.contains("<th scope=\"row\">1</th>")).isTrue();
        assertThat(profilePage.contains("희봉")).isTrue();
    }


    @Test
    void generateBody() throws IOException {
        DataBase.deleteAll();
        User user1 = new User("heebongId1", "password", "heebong1", "javajigi@gmail.com");
        User user2 = new User("heebongId2", "password", "희봉", "javajigi@gmail.com");
        DataBase.addUser(user1);
        DataBase.addUser(user2);
        Map<String, Object> users = new HashMap<>();
        users.put("users", DataBase.findAll());

        UserListServlet userListServlet = new UserListServlet();
        String userListPage = new String(userListServlet.generateBody());

        System.out.println(userListPage);
        assertThat(userListPage.contains("heebongId1")).isTrue();
        assertThat(userListPage.contains("<th scope=\"row\">1</th>")).isTrue();
        assertThat(userListPage.contains("희봉")).isTrue();
    }
}