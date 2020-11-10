package web.cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpCookieTest {

    @DisplayName("쿠키를 응답 헤더의 형태로 변환한다.")
    @Test
    void write() {
        //given
        HttpCookie httpCookie = new HttpCookie("session", "123");

        //when
        String result = httpCookie.write();

        //then
        assertThat(result).isEqualTo("Set-Cookie: session=123" + System.lineSeparator());
    }

    @DisplayName("옵션이 존재하는 쿠키를 응답 헤더의 형태로 변환한다.")
    @Test
    void write2() {
        //given
        HttpCookie httpCookie = new HttpCookie("session", "123");
        httpCookie.addOption(CookieOption.PATH, "/");
        httpCookie.addOption(CookieOption.HTTP_ONLY);

        //when
        String result = httpCookie.write();

        //then
        assertThat(result).isEqualTo("Set-Cookie: session=123; Path=/; HttpOnly" + System.lineSeparator());
    }
}