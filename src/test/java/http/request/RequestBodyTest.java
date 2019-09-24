package http.request;

import http.exception.NotFoundDataException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestBodyTest {

    @Test
    void 생성_성공_테스트() {
        String queryString = "";
        String bodyData = "userId=seon&password=password&name=sos&email=sos@sos.sos";
        RequestBody body = new RequestBody(bodyData);
        assertThat(body.getData("userId")).isEqualTo("seon");
        assertThat(body.getData("password")).isEqualTo("password");
        assertThat(body.getData("name")).isEqualTo("sos");
        assertThat(body.getData("email")).isEqualTo("sos@sos.sos");
    }

    @Test
    void 존재하지_않는_데이터_에러_테스트() {
        String queryString = "";
        String bodyData = "password=password&name=sos&email=sos@sos.sos";
        RequestBody body = new RequestBody(bodyData);
        assertThrows(NotFoundDataException.class, () -> body.getData("userId"));
    }
}