package web;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static utils.HttpRequestParser.*;
import static web.HttpRequestFixture.*;
import static web.RequestHeader.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHeaderTest {
    static RequestHeader createHeader(String method, String uri) throws IOException {
        String request = method + " " + uri + " HTTP/1.1" + NEW_LINE
                + "Host: localhost:8080" + NEW_LINE
                + "Connection: keep-alive" + NEW_LINE
                + "Content-Length: " + JAVAJIGI_DATA.length() + NEW_LINE
                + "Accept: */*" + NEW_LINE
                + EMPTY;
        BufferedReader br = createBufferedReader(request);
        return new RequestHeader(br);
    }

    @DisplayName("RequestHeader를 생성한다.")
    @Test
    public void RequestHeader() throws IOException {
        assertThat(createHeader(GET, INDEX_HTML))
                .isInstanceOf(RequestHeader.class);
    }

    @DisplayName("정적 파일에 대한 요청인지 확인한다.")
    @Test
    public void isStaticFile() throws IOException {
        assertTrue(createHeader(GET, INDEX_HTML).isStaticFile());
    }

    @DisplayName("POST 요청인지 확인한다.")
    @Test
    public void isPost() throws IOException {
        assertTrue(createHeader(POST, ROOT).isPost());
    }

    @DisplayName("요청의 Uri를 추출한다.")
    @Test
    public void getRequestUri() throws IOException {
        RequestUri expected = new RequestUri(INDEX_HTML);

        RequestUri actual = createHeader(GET, INDEX_HTML).getRequestUri();

        assertEquals(expected, actual);
    }
}