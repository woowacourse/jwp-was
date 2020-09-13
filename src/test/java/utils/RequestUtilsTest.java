package utils;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestUtilsTest {

    private final String getSignInRequestUrl = "GET /user/create?userId=Id&password=password&name=name&email=email@gmail.com HTTP/1.1";
    private final String[] requestUrlArrays = RequestUtils.separateUrl(getSignInRequestUrl);

    @DisplayName("Http 요청이 들어오면 http 요청 정보를 분리한다")
    @Test
    void separateHttpRequest() {

        assertThat(requestUrlArrays[0]).isEqualTo("GET");
        assertThat(requestUrlArrays[1]).isEqualTo("/user/create?userId=Id&password=password&name=name&email=email@gmail.com");
        assertThat(requestUrlArrays[2]).isEqualTo("HTTP/1.1");
    }

    @DisplayName("Url 요청이 회원가입일 경우 유저 정보가 담긴 url을 분리한다.")
    @Test
    void extractUserInfoTest1() {

        boolean isSignin = RequestUtils.isSignIn(requestUrlArrays);

        assertThat(isSignin).isTrue();
    }

    @DisplayName("Url 요청이 회원가입 요청이 아닐 경우 Url을 그대로 반환한다")
    @Test
    void extractUserInfoTest2() {

        String postUrl = "POST /user/create?userId=Id&password=password&name=name&email=email@gmail.com HTTP/1.1";

        boolean isSignIn = RequestUtils.isSignIn(RequestUtils.separateUrl(postUrl));

        assertThat(isSignIn).isFalse();
    }

    @DisplayName("회원가입 정보가 담긴 Url을 HashMap으로 변환해준다.")
    @Test
    void parseUserInfoTest() {
        String userInfoUrl = "userId=Id&password=password&name=name&email=email@gmail.com";
        HashMap<String, String> userInfo = RequestUtils.parseUserInfo(userInfoUrl);

        assertThat(userInfo.get("userId")).isEqualTo("Id");
        assertThat(userInfo.get("password")).isEqualTo("password");
        assertThat(userInfo.get("name")).isEqualTo("name");
        assertThat(userInfo.get("email")).isEqualTo("email@gmail.com");
    }
}