package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ParamUtilsTest {
    @DisplayName("String 형식의 parameter로 부터 parameter map을 만드는 기능 테스트")
    @Test
    void createParams() {
        String paramOfRequestLine = "userId=id&password=1234&name=name";

        Map<String, String> params = ParamUtils.createParams(paramOfRequestLine);

        assertAll(
                () -> assertThat(params.get("userId")).isEqualTo("id"),
                () -> assertThat(params.get("password")).isEqualTo("1234"),
                () -> assertThat(params.get("name")).isEqualTo("name"),
                () -> assertThat(params).hasSize(3)
        );
    }

    @DisplayName("createParams에 공란이 들어왔을 때 테스트")
    @Test
    void createParamsWithEmptyString() {
        String paramOfRequestLine = "";

        Map<String, String> params = ParamUtils.createParams(paramOfRequestLine);

        assertThat(params).hasSize(0);
    }
}
