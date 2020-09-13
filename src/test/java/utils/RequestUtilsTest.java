package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import db.DataBase;
import model.User;

class RequestUtilsTest {

    private final String getSignInRequestUrl = "POST /user/create?userId=Id&password=password&name=name&email=email@gmail.com HTTP/1.1";
    private final String[] requestUrlArrays = RequestUtils.separateUrl(getSignInRequestUrl);

    @DisplayName("Http 요청이 들어오면 http 요청 정보를 분리한다")
    @Test
    void separateHttpRequest() {

        assertThat(requestUrlArrays[0]).isEqualTo("POST");
        assertThat(requestUrlArrays[1]).isEqualTo("/user/create?userId=Id&password=password&name=name&email=email@gmail.com");
        assertThat(requestUrlArrays[2]).isEqualTo("HTTP/1.1");
    }

    @DisplayName("Url 요청이 회원가입일 경우 index.html 을 반환한다.")
    @Test
    void extractUserInfoTest1() {

        String result = RequestUtils.signIn(requestUrlArrays, "userId=Id&password=password&name=name&email=email@gmail.com");

        assertThat(result).isEqualTo("index.html");
    }

    @DisplayName("Url 요청이 회원가입 요청이 아닐 경우 Url을 그대로 반환한다")
    @Test
    void extractUserInfoTest2() {

        String postUrl = "GET /user/create?userId=Id&password=password&name=name&email=email@gmail.com HTTP/1.1";

        String result = RequestUtils.signIn(postUrl.split(" "), "userId=Id&password=password&name=name&email=email@gmail.com");

        assertThat(result).isEqualTo("/user/create?userId=Id&password=password&name=name&email=email@gmail.com");
    }

    @DisplayName("회원가입 정보 Database에 저장된다.")
    @Test
    void parseUserInfoTest() {
        String userId = "Id";
        RequestUtils.signIn(requestUrlArrays, "userId=Id&password=password&name=name&email=email@gmail.com");

        User user = DataBase.findUserById(userId);
        assertThat(user.getName()).isEqualTo("name");
    }
}