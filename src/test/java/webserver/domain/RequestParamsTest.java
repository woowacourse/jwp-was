package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestParamsTest {

    private static final String REQUEST_PARAMS_WITH_EMPTY_VALUE = "name=&value=";

    @DisplayName("URI의 query에 빈 값이 들어갔을 경우 빈값이 잘 들어가는지 확인 한다.")
    @Test
    void emptyValue() throws UnsupportedEncodingException {
        RequestParams requestParams = new RequestParams(REQUEST_PARAMS_WITH_EMPTY_VALUE);
        Map<String, String> params = requestParams.getParams();
        assertAll(
            () -> assertThat(params.get("name")).isEqualTo(""),
            () -> assertThat(params.get("value")).isEqualTo("")
        );
    }
}
