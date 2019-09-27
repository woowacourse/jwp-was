package servlet.view;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.response.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewTest {
    private static final Logger logger = LoggerFactory.getLogger(ViewTest.class);

    @Test
    @DisplayName("단순 객체를 넣었을 때 View 테스트")
    public void viewProfile() throws IOException {
        HttpServletResponse httpServletResponse  = new HttpServletResponse();
        httpServletResponse.forward("/user/profile.html");
        httpServletResponse.addParameter("name", "자바지기");
        httpServletResponse.addParameter("email", "javajigi@gmail.com");
        View view = ViewResolver.resolve(httpServletResponse);
        logger.info(view.getPage());
    }


    @Test
    @DisplayName("컬렉션을 파라미터로 넣었을 때 View 테스트")
    public void viewUserList() throws IOException {
        HttpServletResponse httpServletResponse  = new HttpServletResponse();
        httpServletResponse.forward("/user/list.html");

        DataBase.addUser(new User("javajigi1", "password", "자바지기1", "javajigi1@gmail.com"));
        DataBase.addUser(new User("javajigi2", "password", "자바지기2", "javajigi2@gmail.com"));
        List<User> users = new ArrayList<>(DataBase.findAll());

        httpServletResponse.addParameter("users", users);
        View view = ViewResolver.resolve(httpServletResponse);
        logger.info(view.getPage());
    }

    @Test
    @DisplayName("파라미터가 없을 때 /index.html 테스트")
    public void viewIndex() throws IOException {
        HttpServletResponse httpServletResponse  = new HttpServletResponse();
        httpServletResponse.forward("/index.html");
        View view = ViewResolver.resolve(httpServletResponse);
        logger.info(view.getPage());
    }
}