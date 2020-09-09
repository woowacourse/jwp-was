package webserver.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RequestHeaderTest {

    private static final String GET_HEADER =
        "GET /user/create?userId=javajigi&password=password&name=pobi&email=javajigi%40slipp.net HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";

    private static final String POST_HEADER = "POST /user/create HTTP/1.1\n"
        + "Host: localhost:8080\n"
        + "Connection: keep-alive\n"
        + "Content-Length: 59\n"
        + "Content-Type: application/x-www-form-urlencoded\n"
        + "Accept: */*";

    private static Stream<Arguments> headerProvider() {
        return Stream.of(
            Arguments.of(GET_HEADER, true),
            Arguments.of(POST_HEADER, false)
        );
    }

    @DisplayName("헤더를 입력받아서 RequestHeader 클래스에 담기는지 확인한다.")
    @Test
    void getRequestMethod() throws UnsupportedEncodingException {
        RequestHeader requestHeader = new RequestHeader(POST_HEADER);
        assertAll(
            () -> assertThat(requestHeader.getRequestMethod()).isEqualTo(RequestMethod.POST),
            () -> assertThat(requestHeader.getPath()).isEqualTo("/user/create"),
            () -> assertThat(requestHeader.findContentLength()).isEqualTo(59)
        );
    }

    @DisplayName("헤더 Url에 RequestParameter가 존재하는지 확인한다.")
    @ParameterizedTest
    @MethodSource("headerProvider")
    void hasQueryParam() throws UnsupportedEncodingException {
        RequestHeader requestHeader = new RequestHeader(GET_HEADER);
        assertThat(requestHeader.hasQueryParam()).isTrue();
    }
}
