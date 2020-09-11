package utils;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RequestUtilsTest {

    @DisplayName("Request Line에서 QueryString 추출")
    @Test
    void extractQueryString() {
        String requestLine = "/user/create?userId=javajigi&password=password&name=name&email=javajigi@slipp.net";

        String queryString = RequestUtils.extractQueryString(requestLine);

        assertThat(queryString).isEqualTo("userId=javajigi&password=password&name=name&email=javajigi@slipp.net");
    }

    @DisplayName("Request Line에서 QueryString이 없을 때 예외 발생")
    @Test
    void extractQueryString2() {
        String requestLine = "/user/create";

        assertThatThrownBy(() -> RequestUtils.extractQueryString(requestLine))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("QueryString을 포함하고 있지 않습니다.");
    }

    @DisplayName("input data에서 User 객체로 변환")
    @Test
    void createUser() {
        String input = "userId=javajigi&password=password&name=name&email=javajigi@slipp.net";

        User actual = RequestUtils.createUser(input);
        User expected = new User("javajigi", "password", "name", "javajigi@slipp.net");

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @DisplayName("올바른 input data가 아닐 때")
    @Test
    void createUser2() {
        String input = "userId=&password=password&name=name&email=javajigi@slipp.net";

        User actual = RequestUtils.createUser(input);
        User expected = new User("javajigi", "password", "name", "javajigi@slipp.net");

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

}