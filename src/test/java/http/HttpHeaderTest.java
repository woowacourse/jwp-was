package http;

import http.exception.InvalidHeaderFormatException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpHeaderTest {
    private static final String HEADER_FORM = "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Cache-Control: max-age=0\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36\n" +
            "Sec-Fetch-Mode: navigate\n" +
            "Sec-Fetch-User: ?1\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,/;q=0.8,application/signed-exchange;v=b3\n" +
            "Sec-Fetch-Site: none\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n" +
            "Cookie: Idea-c5a8acf3=c2c6d2e2-54d7-47a9-8e2a-b0ff8e06a759; JSESSIONID=55F183C31FC99489F6D47D0794FD685F\n";

    @Test
    void 헤더_정상_생성() {
        List<String> headerLines = Arrays.asList(HEADER_FORM.split("\n"));

        assertDoesNotThrow(() -> new HttpHeader(headerLines));
    }

    @Test
    void 헤더_형식_에러() {
        List<String> headerLines = new ArrayList<>(Arrays.asList(HEADER_FORM.split("\n")));
        headerLines.add("HEADER");

        assertThrows(InvalidHeaderFormatException.class, () -> new HttpHeader(headerLines));
    }
}
