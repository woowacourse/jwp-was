package webserver.http;

import exception.InvalidParameterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueryStringTest {

    @DisplayName("올바른 Query String 형식의 값으로 QueryString 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"", "id=abc", "id=abc&name=%c%e%f&password=0"})
    void fromTest(String queryString) {
        assertThat(QueryString.from(queryString)).isInstanceOf(QueryString.class);
    }

    @DisplayName("잘못된 Query String 형식의 값으로 QueryString 객체 생성을 시도하면 InvalidParameterException 발생")
    @ParameterizedTest
    @ValueSource(strings = {"id=abc&name", "name&password=2&e", "email", "id&name&password", "=abc&=0", "=%c%e%f"})
    void fromExceptionTest(String invalidQueryString) {
        assertThatThrownBy(() -> QueryString.from(invalidQueryString))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessageContaining("정상적인 Parameter 형식이 아닙니다! -> ");
    }

    @DisplayName("QueryString 객체가 알고 있는 Parameter key 값으로 value를 반환")
    @Test
    void getParameterValueTest() {
        String idValue = "abc";
        String passwordValue = "123";
        String nickNameValue = " ";
        String queryStringValue = "id=" + idValue + "&password=" + passwordValue + "&nickname=" + nickNameValue;
        QueryString queryString = QueryString.from(queryStringValue);

        assertThat(queryString.getParameterValue("id")).isEqualTo(idValue.trim());
        assertThat(queryString.getParameterValue("password")).isEqualTo(passwordValue.trim());
        assertThat(queryString.getParameterValue("nickname")).isEqualTo(nickNameValue.trim());
    }

    @DisplayName("QueryString 객체가 모르는 Parameter key 값으로 value를 찾으면 Null 반환")
    @Test
    void getParameterValueExceptionTest() {
        String queryStringValue = "id=abc&password=123";
        QueryString queryString = QueryString.from(queryStringValue);
        String invalidParameterKey = "email";

        assertThat(queryString.getParameterValue(invalidParameterKey)).isNull();
    }

    @DisplayName("QueryString 객체의 Parameter가 있으면 true 반환")
    @ParameterizedTest
    @ValueSource(strings = {"id=abc&password=123", "nickname=coollime"})
    void isNotEmptyTrueTest(String queryStringValue) {
        QueryString queryString = QueryString.from(queryStringValue);

        assertThat(queryString.isNotEmpty()).isTrue();
    }

    @DisplayName("QueryString 객체의 Parameter가 없으면 false 반환")
    @Test
    void isNotEmptyFalseTest() {
        QueryString queryString = QueryString.from("");

        assertThat(queryString.isNotEmpty()).isFalse();
    }
}