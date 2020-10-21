package web.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import db.DataBase;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.HandlebarsHelper;
import web.application.domain.model.User;
import web.application.dto.UserListResponse;

public class HandlebarsHelperTest {

    @BeforeEach
    void setUp() {
        DataBase.addUser(User.builder()
            .userId("javajigi")
            .password("password")
            .name("자바지기")
            .email("javajigi@gmail.com")
            .build());
        DataBase.addUser(User.builder()
            .userId("khb1109")
            .password("password")
            .name("몽")
            .email("khb@gmail.com")
            .build());
    }

    @DisplayName("템플릿 경로와 모델을 입력하면 해당 동적 페이지를 반환한다.")
    @Test
    void handlebarsApplyTest() {
        HandlebarsHelper handlebarsHelper = HandlebarsHelper.getInstance();

        List<User> users = new ArrayList<>(DataBase.findAll());
        UserListResponse userListResponse = UserListResponse.of(users);

        String contents = handlebarsHelper.apply("user/list", userListResponse);

        assertAll(
            () -> assertThat(contents).contains("자바지기"),
            () -> assertThat(contents).contains("몽")
        );
    }
}
