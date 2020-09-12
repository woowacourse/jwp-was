package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ParamUtilsTest {
    @DisplayName("String으로 된 parameter로 부터 data를 map으로 추출하여 put 하는 기능 테스트")
    @Test
    void putParameter() {
        Map<String, String> params = new HashMap<>();
        ParamUtils.putParameter(params, "userId=id&password=1234&name=name");

        assertAll(
                () -> assertThat(params.get("userId")).isEqualTo("id"),
                () -> assertThat(params).hasSize(3)
        );
    }
}
