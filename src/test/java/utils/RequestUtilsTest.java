package utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestUtilsTest {

    @DisplayName("Http 요청이 들어오면 http 요청 정보를 분리한다")
    @Test
    void separateHttpRequest() {
        String requestUrl = "GET /user/create?userId=Id&password=password&name=name&email=email@gmail.com HTTP/1.1";
        String[] requestUrlArrays = RequestUtils.separateUrl(requestUrl);

        assertThat(requestUrlArrays[0]).isEqualTo("GET");
        assertThat(requestUrlArrays[1]).isEqualTo("/user/create?userId=Id&password=password&name=name&email=email@gmail.com");
        assertThat(requestUrlArrays[2]).isEqualTo("HTTP/1.1");
    }
}