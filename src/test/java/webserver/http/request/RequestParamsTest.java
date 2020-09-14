package webserver.http.request;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestParamsTest {
    @DisplayName("생성 테스트")
    @Test
    void from() {
        RequestParams requestParams = RequestParams.from("a=10&b=20&c=0");

        assertThat(requestParams.getParameters()).hasSize(3);
        assertThat(requestParams.getOneParameterValue("a")).isEqualTo("10");
        assertThat(requestParams.getOneParameterValue("b")).isEqualTo("20");
        assertThat(requestParams.getOneParameterValue("c")).isEqualTo("0");
    }
}