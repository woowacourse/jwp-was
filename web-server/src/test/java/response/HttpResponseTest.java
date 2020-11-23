package response;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import resource.ContentType;

class HttpResponseTest {

    @Test
    @DisplayName("302 response with location 만들기")
    void create302ResponseHeader() {
        HttpResponse response = new HttpResponse(StatusCode.FOUND, "/");
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: /\r\n";

        assertThat(response.buildHeader()).isEqualTo(expected);
    }

    @Test
    @DisplayName("쿠키가 있는 response 만들기")
    void createResponseWithCookies() {
        ResponseCookies cookies = new ResponseCookies(
            Arrays.asList(
                new ResponseCookie("token", "1A32Q!#RESRE15039", "/"),
                new ResponseCookie("id", "1", "/"))
        );

        HttpResponse response = new HttpResponse(StatusCode.FOUND, "/")
            .setCookies(cookies);

        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: /\r\n"
            + "Set-Cookie: token=1A32Q!#RESRE15039; Path=/\r\n"
            + "Set-Cookie: id=1; Path=/\r\n";

        assertThat(response.buildHeader()).isEqualTo(expected);
    }

    @Test
    @DisplayName("body 가 있는 응답 만들기")
    void createResponseWithBody() {
        byte[] body = "sdf".getBytes();
        HttpResponse response = new HttpResponse(StatusCode.OK, body, ContentType.HTML);

        String expectedHeader = "HTTP/1.1 200 OK \r\n"
            + "Content-Type: text/html;charset=UTF-8\r\n"
            + "Content-Length: " + body.length + "\r\n"
            + "\r\n";

        assertThat(response.buildHeader()).isEqualTo(expectedHeader);
        assertThat(response.getBody()).isEqualTo(body);
    }

    @Test
    @DisplayName("body 가 비어있는 응답 객체에서 getBody 메서드 사용")
    void getBody_IfBodyIsEmpty() {
        HttpResponse response = new HttpResponse(StatusCode.FOUND);

        assertThat(response.getBody()).isEqualTo(new byte[0]);
    }
}
