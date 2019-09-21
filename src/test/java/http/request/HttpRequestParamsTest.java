package http.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestParamsTest {

    @Test
    void params_생성_테스트() {
        HttpRequestParams requestParams = HttpRequestParams.of("username=duho&password=1234");

        assertEquals("duho", requestParams.get("username"));
        assertEquals("1234", requestParams.get("password"));
    }

    @Test
    void 값이_없는_경우_빈값_확인() {
        HttpRequestParams requestParams = HttpRequestParams.of("username=duho&password=");

        assertEquals("", requestParams.get("password"));
    }
}