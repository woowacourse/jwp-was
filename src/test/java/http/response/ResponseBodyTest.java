package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyTest {
    private ResponseBody responseBody;

    @Test
    @DisplayName("responseBody 기본 생성 테스트")
    void createTest() {
        responseBody = ResponseBody.of();

        assertThat(responseBody).isInstanceOf(ResponseBody.class);
        assertThat(responseBody.getBody()).isEqualTo(new byte[0]);
    }

    @Test
    @DisplayName("responseBody byte[] 입력 생성 테스트")
    void create2Test() {
        responseBody = ResponseBody.of(new byte[3]);

        assertThat(responseBody).isInstanceOf(ResponseBody.class);
        assertThat(responseBody.getBody()).isEqualTo(new byte[3]);
    }
}