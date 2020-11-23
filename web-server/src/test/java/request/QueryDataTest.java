package request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class QueryDataTest {

    private QueryData queryData = new QueryData("id=abcd&password=1234");

    @ParameterizedTest
    @MethodSource("getParameterOfIsUriUsingQueryTest")
    @DisplayName("URI 가 Query String 형식으로 데이터를 전달하고있는지 검사하기")
    void isUriUsingQuery(String uri, boolean expected) {
        assertThat(QueryData.isUriUsingQuery(uri)).isEqualTo(expected);
    }

    private static Stream<Arguments> getParameterOfIsUriUsingQueryTest() {
        return Stream.of(
            Arguments.of("/join?id=abcd&password=1234", true),
            Arguments.of("/join", false)
        );
    }

    @Test
    @DisplayName("query 형식의 데이터에서 필드명을 이용해 값 꺼내기")
    void getQueryData() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "abcd");
        expected.put("password", "1234");

        assertThat(queryData.getQueryData()).isEqualTo(expected);
    }
}
