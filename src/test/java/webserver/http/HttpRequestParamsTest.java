package webserver.http;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestParamsTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        HttpRequestParams httpRequestParams = HttpRequestParams.from("a=10&b=20&c=0");

        assertThat(httpRequestParams.getParameters()).hasSize(3);
        assertThat(httpRequestParams.getOneParameterValue("a")).isEqualTo("10");
        assertThat(httpRequestParams.getOneParameterValue("b")).isEqualTo("20");
        assertThat(httpRequestParams.getOneParameterValue("c")).isEqualTo("0");
    }
}