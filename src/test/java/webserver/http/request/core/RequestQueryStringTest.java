package webserver.http.request.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.UtilData.REQUEST_GET_PARAM_PATH;

class RequestQueryStringTest {
    @Test
    @DisplayName("파라미터 있는 GET 으로 요청 할 경우 Data 추출 한다.")
    void RequestGetHasParam() {
        RequestQueryString requestQueryString = new RequestQueryString(REQUEST_GET_PARAM_PATH);
        assertThat(requestQueryString.getValue("userId")).isEqualTo("javajigi");
        assertThat(requestQueryString.getValue("password")).isEqualTo("password");
        assertThat(requestQueryString.getValue("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestQueryString.getValue("email")).isEqualTo("javajigi%40slipp.net");
    }
}