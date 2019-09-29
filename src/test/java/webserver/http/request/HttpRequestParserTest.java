package webserver.http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.common.HttpVersion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestParserTest {

    @ParameterizedTest
    @MethodSource("createHttpRequest")
    void HttpRequest_생성(InputStream inputStream) {
        HttpRequest httpRequest = new HttpRequest();
        HttpRequestParser.parse(inputStream, httpRequest);

        assertEquals(HttpMethod.GET, httpRequest.getRequestLine().getHttpMethod());
        assertEquals("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net", httpRequest.getRequestLine().getOriginUrl());
        assertEquals(HttpVersion.HTTP_1_1, httpRequest.getRequestLine().getHttpVersion());

        assertEquals("javajigi", httpRequest.getQueryStringParams().get("userId"));
        assertEquals("password", httpRequest.getQueryStringParams().get("password"));
        assertEquals("%EB%B0%95%EC%9E%AC%EC%84%B1", httpRequest.getQueryStringParams().get("name"));
        assertEquals("javajigi%40slipp.net", httpRequest.getQueryStringParams().get("email"));

        assertEquals("localhost:8080", httpRequest.getHttpHeader().get("Host"));
        assertEquals("keep-alive", httpRequest.getHttpHeader().get("Connection"));
        assertEquals("*/*", httpRequest.getHttpHeader().get("Accept"));
    }

    static Stream<Arguments> createHttpRequest() {
        String str =
                "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n\r";
        return Stream.of(
                Arguments.of(new ByteArrayInputStream(
                        str.getBytes()
                ))
        );
    }
}