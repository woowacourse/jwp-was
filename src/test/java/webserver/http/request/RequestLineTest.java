package webserver.http.request;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.common.HttpVersion;
import webserver.http.request.exception.InvalidRequestLineException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "GET /index.html HTTP/1.1",
            "POST /user/create HTTP/1.1",
            "GET /user/form.html HTTP/1.1"
    })
    void 올바른_Request_Line_생성(String line) {
        RequestLine requestLine = RequestLine.of(line);
        String[] tokens = line.split(" ");

        assertEquals(requestLine.getHttpMethod(), HttpMethod.of(tokens[0]));
        assertEquals(requestLine.getOriginUrl(), tokens[1]);
        assertEquals(requestLine.getHttpVersion(), HttpVersion.of(tokens[2]));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "GET /index.html",
            "GET HTTP/1.1",
            "/index.html HTTP/1.1",
            "POST /user/create HTTP/1.1 뭔가더있어",
    })
    void 올바르지_않은_Request_Line(String line) {
        assertThrows(InvalidRequestLineException.class, () -> {
            RequestLine.of(line);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "GET /index?name=abc&password=1234 HTTP/1.1",
            "POST /index?name= HTTP/1.1",
    })
    void Query_String_이_포함되어있는_경우_분리(String line) {
        assertEquals(line.split(" ")[1].split("\\?")[1], RequestLine.of(line).splitQueryString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "GET /index.html? HTTP/1.1",
            "POST /index? HTTP/1.1"
    })
    void name(String line) {
        assertEquals("", RequestLine.of(line).splitQueryString());
    }
}