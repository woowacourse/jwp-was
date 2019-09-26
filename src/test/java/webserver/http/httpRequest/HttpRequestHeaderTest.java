package webserver.http.httpRequest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderTest {
    String header = "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Pragma: no-cache\n" +
            "Cache-Control: no-cache\n" +
            "Sec-Fetch-Mode: no-cors\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36\n" +
            "Accept: image/webp,image/apng,image/*,*/*;q=0.8\n" +
            "Sec-Fetch-Site: same-origin\n" +
            "Referer: http://localhost:8080/\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7\n\n";

    @Test
    void 객체_생성후_HOST_확인_테스트() {
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.create(header);
        String host = httpRequestHeader.getHost();
        assertThat(host).isEqualTo("localhost:8080");
    }
}