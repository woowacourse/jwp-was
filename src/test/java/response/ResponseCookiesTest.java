package response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ResponseCookiesTest {

    @Test
    @DisplayName("ResponseCookies 생성")
    void create() {
        ResponseCookies cookies = new ResponseCookies(Arrays.asList(
            new ResponseCookie("cookie1", "mint", "/"),
            new ResponseCookie("cookie2", "chocolate", "/"))
        );
        assertThat(cookies).isInstanceOf(ResponseCookies.class);
    }

    @Test
    @DisplayName("ResponseCookies 생성 - 중복된 쿠키이름이 있을 경우 예외처리")
    void create_IfDuplicatedCookieNameExists_ThrowException() {
        assertThatThrownBy(() -> new ResponseCookies(Arrays.asList(
            new ResponseCookie("cookie", "mint", "/"),
            new ResponseCookie("cookie", "chocolate", "/"))
        )).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("cookies with same name exist.");
    }

    @Test
    @DisplayName("ResponseCookies 생성 - 쿠키 한개짜리를 간편하게 생성하기 위한 factory method 이용")
    void createWithSingleCookie() {
        ResponseCookies cookies = ResponseCookies
            .createWithSingleCookie("cookie", "drug flavor cookies", "/");

        assertThat(cookies).isInstanceOf(ResponseCookies.class);
    }

    @Test
    @DisplayName("쿠키 각각을 response header 형식으로 바꾸기")
    void toCookieHeaderValueFormats() {
        ResponseCookies cookies = new ResponseCookies(Arrays.asList(
            new ResponseCookie("cookie1", "mint", "/"),
            new ResponseCookie("cookie2", "chocolate", "/"))
        );
        List<String> expected = new ArrayList<>();

        expected.add("cookie1=mint; Path=/");
        expected.add("cookie2=chocolate; Path=/");

        assertThat(cookies.toCookieHeaderValueFormats()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("testcaseForIsNotEmpty")
    @DisplayName("빈 ResponseCookies 인지 확인")
    void isNotEmpty(ResponseCookies cookies, boolean expected) {
        assertThat(cookies.isNotEmpty()).isEqualTo(expected);
    }

    private static Stream<Arguments> testcaseForIsNotEmpty() {
        ResponseCookies notEmpty = new ResponseCookies(Arrays.asList(
            new ResponseCookie("cookie1", "mint", "/"),
            new ResponseCookie("cookie2", "chocolate", "/"))
        );
        return Stream.of(
            Arguments.of(notEmpty, true),
            Arguments.of(ResponseCookies.EMPTY_COOKIES, false)
        );
    }
}
