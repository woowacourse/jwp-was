package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @Test
    @DisplayName("(key,value)인 쿠키 속성 가져오는 검사")
    void get_attribute_value(){
        Cookie cookie = CookieParser.parse("Set-Cookie: logined=true; Path=/");

        assertThat(cookie.getAttributeValue("logined")).isEqualTo("true");
        assertThat(cookie.getAttributeValue("Path")).isEqualTo("/");
    }

    @Test
    @DisplayName("HttpOnly와 같이 (key,value)가 아닌 속성 검사")
    void get_Attribute_without_value(){
        Cookie cookie = CookieParser.parse("Set-Cookie: logined=true; Path=/; HttpOnly");

        assertThat(cookie.hasContainsAttributeWithoutValue("HttpOnly")).isTrue();
    }
}