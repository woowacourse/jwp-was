package response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import resource.ContentType;

class HttpResponseTest {

    @Test
    @DisplayName("응답 헤더 문자열 만들기")
    void buildHeader() {
        HttpResponse response = new HttpResponse(StatusCode.FOUND, "/");
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: /\r\n";

        assertThat(response.buildHeader()).isEqualTo(expected);
    }

    @Test
    @DisplayName("응답 body 얻기")
    void getBody() {
        byte[] body = "sdf".getBytes();
        HttpResponse response = new HttpResponse(StatusCode.FOUND, body, ContentType.HTML);

        assertThat(response.getBody()).isEqualTo(body);
    }

    @Test
    @DisplayName("응답 body 얻기 - body 가 비어있는 경우")
    void getBody_IfBodyIsEmpty() {
        HttpResponse response = new HttpResponse(StatusCode.FOUND);

        assertThat(response.getBody()).isEqualTo(new byte[0]);
    }
}
