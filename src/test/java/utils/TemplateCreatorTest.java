package utils;

import db.DataBase;
import model.User;
import model.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateCreatorTest {
    private static final Logger log = LoggerFactory.getLogger(TemplateCreator.class);

    @DisplayName("출력을 원하는 템플릿 경로와 출력할 객체를 전달하면 동적으로 템플릿을 잘 만드는지 확인")
    @Test
    void createTemplateTest() {
        String userId = "coollime";
        String password = "1234";
        String name = "쿨라임";
        String email = "coollime@woowa.com";
        DataBase.addUser(new User(userId, password, name, email));

        String userListTemplateLocation = "/user/list";
        Users users = UserService.findAll();

        String template = TemplateCreator.createTemplate(userListTemplateLocation, users);

        log.debug("UserListPage : {}", template);

        assertThat(template).contains(userId, name, email);
    }
}