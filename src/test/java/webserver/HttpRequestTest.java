package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpRequestTest {
    @DisplayName("헤더에서 요청 url을 추출하여 반환한다.")
    @Test
    void getFilePath_whenRequestTemplatesFiles() {
        String requestLine = "GET /index.html HTTP/1.1";
        String header = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n"
            + "Sec-Fetch-Site: none\n"
            + "Sec-Fetch-Mode: navigate\n"
            + "Sec-Fetch-User: ?1\n"
            + "Sec-Fetch-Dest: document\n"
            + "Accept-Encoding: gzip, deflate, br\n"
            + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,vi;q=0.5,la;q=0.4\n"
            + "Cookie: Idea-3be1aa82=33bac591-b163-42cb-9b63-333572a05b11";
        HttpRequest httpRequest = new HttpRequest(requestLine, header);

        assertThat(httpRequest.getFilePath()).isEqualTo("./templates/index.html");
    }

    @DisplayName("헤더에서 요청 url을 추출하여 반환한다.")
    @Test
    void getFilePath_whenRequestStaticFiles() {
        String requestLine = "GET /css/styles.css HTTP/1.1";
        String header = "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36\n"
            + "Accept: text/css,*/*;q=0.1\n"
            + "Sec-Fetch-Site: same-origin\n"
            + "Sec-Fetch-Mode: no-cors\n"
            + "Sec-Fetch-Dest: style\n"
            + "Referer: http://localhost:8080/index.html\n"
            + "Accept-Encoding: gzip, deflate, br\n"
            + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,vi;q=0.5,la;q=0.4\n"
            + "Cookie: Idea-3be1aa82=33bac591-b163-42cb-9b63-333572a05b11\n";
        HttpRequest httpRequest = new HttpRequest(requestLine, header);

        assertThat(httpRequest.getFilePath()).isEqualTo("./static/css/styles.css");
    }
}