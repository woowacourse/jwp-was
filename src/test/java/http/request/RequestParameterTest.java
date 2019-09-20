package http.request;

import http.request.exception.InvalidQueryStringException;
import http.request.exception.ParameterNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestParameterTest {
    @Test
    void 정상_생성() {
        String queryString1 = "id=1&password=abcd1234";
        String queryString2 = "password=abcd1234&id=1";
        RequestParameter requestParameter = new RequestParameter(queryString1);

        assertThat(requestParameter).isEqualTo(new RequestParameter(queryString2));
    }

    @Test
    void 생성_에러_query_string이_null() {
        assertThrows(InvalidQueryStringException.class, () -> new RequestParameter(null));
    }

    @Test
    void getParameter_value가_있을_때() {
        String key = "1";
        String value = "2";
        RequestParameter requestParameter = new RequestParameter(String.format("%s=%s", key, value));

        assertThat(requestParameter.getParameter(key)).isEqualTo(value);
    }

    @Test
    void getParameter_value가_없을_때() {
        RequestParameter requestParameter = new RequestParameter("1=");

        assertThrows(ParameterNotFoundException.class, () -> requestParameter.getParameter("1"));
    }

    @ParameterizedTest
    @MethodSource("provideInputOutputs")
    void parse(String input, RequestParameter requestParameter) {
        assertThat(new RequestParameter(input)).isEqualTo(requestParameter);
    }

    private static Stream<Arguments> provideInputOutputs() {
        RequestParameter requestParameter = new RequestParameter("");
        return Stream.of(
                Arguments.of("&", requestParameter),
                Arguments.of("=", requestParameter)
        );
    }
}