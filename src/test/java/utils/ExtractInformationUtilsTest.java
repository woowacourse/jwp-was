package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractInformationUtilsTest {

    @Test
    @DisplayName("입력된_쿼리_정보_추출")
    void extractInformation() {
        String query = "userId=harry&password=harry123&name=harry&email=harry@naver.com";

        Map<String, String> userInfo = ExtractInformationUtils.extractInformation(query);

        assertThat(userInfo.get("userId")).isEqualTo("harry");
        assertThat(userInfo.get("password")).isEqualTo("harry123");
        assertThat(userInfo.get("name")).isEqualTo("harry");
        assertThat(userInfo.get("email")).isEqualTo("harry@naver.com");
    }

    @Test
    @DisplayName("확장자_추출")
    void extractExtension() {
        String url = "/index.html";
        String createUrl = "/user/create";

        assertThat(ExtractInformationUtils.extractExtension(url)).isEqualTo("html");
        assertThat(ExtractInformationUtils.extractExtension(createUrl)).isEqualTo("/user/create");

    }
}