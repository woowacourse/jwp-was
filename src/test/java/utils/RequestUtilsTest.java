package utils;

import domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RequestUtilsTest {

    @DisplayName("input data에서 User 객체로 변환")
    @Test
    void createUser() {
        String input = "userId=javajigi&password=password&name=name&email=javajigi@slipp.net";

        User actual = RequestUtils.createUser(input);
        User expected = new User("javajigi", "password", "name", "javajigi@slipp.net");

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("올바른 input data가 아닐 때 User 객체로 변환 예외 발생")
    @Test
    void createUser2() {
        String input = "userId=&password=password&name=name&email=javajigi@slipp.net";

        assertThatThrownBy(() -> RequestUtils.createUser(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 형식의 input data:");
    }

}