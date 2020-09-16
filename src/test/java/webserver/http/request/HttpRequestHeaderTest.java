package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.GetHttpResponse;
import webserver.http.response.HttpResponse;
import webserver.http.response.PostHttpResponse;

import java.io.*;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestHeaderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestHeaderTest.class);
    static final String GET_REQUEST_HEADER_NO_PARAMETER = "GET /index.html HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*\n";
    static final String GET_REQUEST_HEADER_WITH_PARAMETER = "GET /user/create?userId=javajigi"
            + "&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Accept: */*";
    static final String POST_REQUEST_WITH_BODY = "POST /user/create HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Content-Length: 93\n"
            + "Content-Type: application/x-www-form-urlencoded\n"
            + "Accept: */*\n"
            + "\n"
            + "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

    private static Stream<Arguments> provideRequestForHttpResponse() {
        return Stream.of(
                Arguments.of(GET_REQUEST_HEADER_NO_PARAMETER, new GetHttpResponse()),
                Arguments.of(GET_REQUEST_HEADER_WITH_PARAMETER, new GetHttpResponse()),
                Arguments.of(POST_REQUEST_WITH_BODY, new PostHttpResponse())
        );
    }

    @DisplayName("HTTP Request Header의 모든 라인을 출력한다.")
    @Test
    void printAllHttpRequestHeaderTest() throws IOException {
        InputStream inputStream = new ByteArrayInputStream(GET_REQUEST_HEADER_NO_PARAMETER.getBytes());
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();
        while (!"".equals(line)) {
            if (Objects.isNull(line)) {
                break;
            }
            LOGGER.info(line);
            line = bufferedReader.readLine();
        }
    }

    @DisplayName("요청에 맞는 응답을 반환하는지 테스트")
    @ParameterizedTest
    @MethodSource("provideRequestForHttpResponse")
    void getHttpResponseTest(String request, HttpResponse httpResponse) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(inputStream);
        HttpResponse expected = httpRequestHeader.getHttpResponse();

        assertThat(expected).isInstanceOf(httpResponse.getClass());
    }
}
