package http;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpBodyTest {

    @Test
    void HTTP_본문_정상_생성() {
        String body = "HTTP body";
        assertDoesNotThrow(() -> new HttpBody(body));
    }

    @Test
    void 쿼리스트링_포함한_본문에서_쿼리스트링_추가() {
        List<String> headerLinesWithQueryString = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*");
        HttpHeader header = new HttpHeader(headerLinesWithQueryString);

        String bodyWithQueryString = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        HttpBody body = new HttpBody(bodyWithQueryString);
        assertTrue(body.addQueryString(header, new QueryString()));
    }

    @Test
    void 쿼리스트링_포함하지않은_본문에서_쿼리스트링_추가() {
        List<String> headerLinesWithoutQueryString = Arrays.asList(
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        HttpHeader header = new HttpHeader(headerLinesWithoutQueryString);

        String normalBody = "Body Test";
        HttpBody body = new HttpBody(normalBody);
        assertFalse(body.addQueryString(header, new QueryString()));
    }
}
