package webserver.http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.common.HttpHeader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryStringParamsParserTest {

    @ParameterizedTest
    @MethodSource("httpGetRequest")
    void GET_요청에_QueryStringParameter_가_있는_경우(String httpRequestLine, List<String> headerLines, String httpBody) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(httpBody.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));

        QueryStringParams queryStringParams = QueryStringParamsParser.parse(bufferedReader, RequestLine.of(httpRequestLine), new HttpHeader(headerLines));

        assertEquals(queryStringParams.toString(), QueryStringParams.of(getParameterLines(httpRequestLine.split(" "))).toString());
    }

    private String getParameterLines(String[] lines) {
        String[] tokens = lines[1].split("\\?");

        if (tokens.length == 2) {
            return tokens[1];
        }

        return "";
    }

    static Stream<Arguments> httpGetRequest() {
        return Stream.of(
                Arguments.of(
                        "GET /index?name=abc&password=123 HTTP/1.1",
                        Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*"),
                        ""
                ),
                Arguments.of(
                        "GET /index?name= HTTP/1.1",
                        Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*"),
                        ""
                ),
                Arguments.of(
                        "GET /index? HTTP/1.1",
                        Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*"),
                        ""
                )
        );
    }

    @ParameterizedTest
    @MethodSource("httpPostRequest")
    void POST_요청에_QueryStringParameter_가_있는_경우(String httpRequestLine, List<String> headerLines, String httpBody) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(httpBody.getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));

        QueryStringParams queryStringParams = QueryStringParamsParser.parse(bufferedReader, RequestLine.of(httpRequestLine), new HttpHeader(headerLines));

        assertEquals(queryStringParams.toString(), QueryStringParams.of(httpBody).toString());
    }

    static Stream<Arguments> httpPostRequest() {
        return Stream.of(
                Arguments.of(
                        "POST /index HTTP/1.1",
                        Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*", "Content-Length: 21"),
                        "name=abc&password=123"
                ),
                Arguments.of(
                        "POST /index HTTP/1.1",
                        Arrays.asList("Host: localhost:8080", "Connection: keep-alive", "Accept: */*", "Content-Length: 0"),
                        ""
                )
        );
    }
}