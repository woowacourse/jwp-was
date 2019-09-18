package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractInformationUtilsTest {

    @Test
    void extractInformation() {
        String query = "/user/create?userId=harry&password=harry123&name=harry&email=harry@naver.com";

        Map<String, String> userInfo = ExtractInformationUtils.extractInformation(query);

        assertThat(userInfo.get("userId")).isEqualTo("harry");
        assertThat(userInfo.get("password")).isEqualTo("harry123");
        assertThat(userInfo.get("name")).isEqualTo("harry");
        assertThat(userInfo.get("email")).isEqualTo("harry@naver.com");
    }
}