package service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import db.DataBase;
import web.application.domain.model.User;

class UserServiceTest {

    @BeforeEach
    void setUp() {
        User user = User.builder()
            .name("pobi")
            .email("test@naver.com")
            .password("1234")
            .userId("pobibi")
            .build();
        DataBase.addUser(user);
    }

    @AfterEach
    void tearDown() {
        DataBase.deleteUser("pobibi");
    }

    @DisplayName("데이터베이스를 통해서 ID와 패스워드가 같은유저인지 확인한다..")
    @ParameterizedTest
    @CsvSource(value = {"pobibi,1234,true", "pobibi,1111,false", "1234,1234,false"})
    void isValidatedUser(String id, String password, boolean expected) {
        UserService userService = UserService.getInstance();
        boolean actual = userService.isValidatedUser(id, password);

        assertThat(actual).isEqualTo(expected);
    }
}