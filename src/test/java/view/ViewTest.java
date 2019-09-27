package view;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.view.View;
import servlet.view.ViewResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewTest {
    private static final Logger logger = LoggerFactory.getLogger(ViewTest.class);

    @Test
    @DisplayName("/user/list.html View 테스트")
    public void templateView() throws IOException {
        DataBase.addUser(
                new User("javajigi1", "password", "자바지기1", "javajigi1@gmail.com"));
        DataBase.addUser(
                new User("javajigi2", "password", "자바지기2", "javajigi2@gmail.com"));
        List<User> users = new ArrayList<>(DataBase.findAll());

        View view = ViewResolver.resolve("/user/list.html");
        view.addModel("users", users);
        logger.info(view.render());
    }
}