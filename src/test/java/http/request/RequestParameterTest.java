package http.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.BLANK;

class RequestParameterTest {
    private static Stream<Arguments> provideInputOutputs() {
        RequestParameter requestParameter = new RequestParameter("");
        return Stream.of(
                Arguments.of("&", requestParameter),
                Arguments.of("=", requestParameter)
        );
    }

    @Test
    void 정상_생성() {
        String queryString1 = "id=1&password=abcd1234";
        String queryString2 = "password=abcd1234&id=1";
        RequestParameter requestParameter = new RequestParameter(queryString1);

        assertThat(requestParameter).isEqualTo(new RequestParameter(queryString2));
    }

    @Test
    void 정상_생성2() {
        String queryString = "id=1&password=pw&name=";
        RequestParameter requestParameter = new RequestParameter(queryString);

        assertThat(requestParameter.getParameter("id")).isEqualTo("1");
        assertThat(requestParameter.getParameter("name")).isNull();
        assertThat(requestParameter.getParameter("address")).isNull();
    }

    @Test
    void query_string이_null일_때_빈_Request_parameter_생성() {
        assertEquals(new RequestParameter(null), new RequestParameter(BLANK));
    }

    @Test
    void getParameter_value가_있을_때() {
        String key = "1";
        String value = "2";
        RequestParameter requestParameter = new RequestParameter(String.format("%s=%s", key, value));

        assertThat(requestParameter.getParameter(key)).isEqualTo(value);
    }

    @ParameterizedTest
    @MethodSource("provideInputOutputs")
    void parse(String input, RequestParameter requestParameter) {
        assertThat(new RequestParameter(input)).isEqualTo(requestParameter);
    }
}