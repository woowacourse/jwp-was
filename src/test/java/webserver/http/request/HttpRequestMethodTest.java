package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.response.GetHttpResponse;
import webserver.http.response.HttpResponse;
import webserver.http.response.PostHttpResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestMethodTest {
    private static Stream<Arguments> provideHttpMethodTypeForHttpResponse() {
        return Stream.of(
                Arguments.of("GET", new GetHttpResponse()),
                Arguments.of("POST", new PostHttpResponse())
        );
    }

    @DisplayName("입력값에 대응되는 HttpResponse를 반환하는지 테스트")
    @ParameterizedTest
    @MethodSource("provideHttpMethodTypeForHttpResponse")
    void getHttpResponseTest(String methodType, HttpResponse httpResponse) {
        HttpResponse expected = HttpRequestMethod.getHttpResponse(methodType);

        assertThat(expected).isInstanceOf(httpResponse.getClass());
    }
}
