package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelAndViewTest {
    private static final Logger logger = LoggerFactory.getLogger(ModelAndViewTest.class);

    @Test
    void Handlebars_test() {
        User user1 = new User("park1", "park", "park1", "park1@naver.com");
        User user2 = new User("park2", "park", "park2", "park2@naver.com");
        User user3 = new User("park3", "park", "park3", "park3@naver.com");

        DataBase.addUser(user1);
        DataBase.addUser(user2);
        DataBase.addUser(user3);
        Collection<User> users = DataBase.findAll();

        Model model = new Model();
        model.setAttribute("users", users);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile("user/list");
            String listPage = template.apply(model.getAttributes());
            logger.debug(listPage);
            assertThat(listPage).contains("park1");
            assertThat(listPage).contains("park1@naver.com");
            assertThat(listPage).contains("park2");
            assertThat(listPage).contains("park2@naver.com");
            assertThat(listPage).contains("park3");
            assertThat(listPage).contains("park3@naver.com");
        } catch (IOException e) { }
    }
}
