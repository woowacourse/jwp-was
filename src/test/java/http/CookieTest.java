package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {

    @Test
    @DisplayName("여러개의 name-value 쌍이 존재하는 경우")
    void fromCookie() {
        String cookieFromHeader = "JSESSIONID=1B67FCFA4B6548D5F86799E6E6CCC2D2; NNB=KJ6H2RUSNSHV2";

        Cookie cookie = Cookie.fromCookie(cookieFromHeader);

        String notExistName = "notExistName";
        assertThat(cookie.size()).isEqualTo(2);
        assertThat(cookie.getValue("JSESSIONID").get()).isEqualTo("1B67FCFA4B6548D5F86799E6E6CCC2D2");
        assertThat(cookie.getValue("NNB").get()).isEqualTo("KJ6H2RUSNSHV2");
        assertThat(cookie.getValue(notExistName)).isEqualTo(Optional.empty());
    }

    @Test
    void add() {
        Cookie cookie = Cookie.fromCookie("");
        assertThat(cookie.size()).isEqualTo(0);


        String name1 = "name1";
        cookie.add(name1, "value1");
        assertThat(cookie.size()).isEqualTo(1);
        assertThat(cookie.getValue(name1).get()).isEqualTo("value1");

        cookie.add(name1, "value2");
        assertThat(cookie.size()).isEqualTo(1);
        assertThat(cookie.getValue(name1).get()).isEqualTo("value2");


        String name2 = "name2";
        cookie.add(name2, "value1");
        assertThat(cookie.size()).isEqualTo(2);
        assertThat(cookie.getValue(name2).get()).isEqualTo("value1");
    }

    @Test
    void toHeaderValue() {
        List<String> cookieSequences = Arrays.asList(
                "JSESSIONID=1B67FCFA4B6548D5F86799E6E6CCC2D2;",
                "NNB=KJ6H2RUSNSHV2"
        );
        Cookie cookie = Cookie.fromCookie("".join("", cookieSequences));

        String headerValue = cookie.toHeaderValue();
        assertThat(headerValue.contains("path=/")).isTrue();
        for (String cookieSequence : cookieSequences) {
            assertThat(headerValue.contains(cookieSequence)).isTrue();
        }
    }
}