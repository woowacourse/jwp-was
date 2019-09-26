package webserver;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import webserver.http.HttpRequest;
import webserver.http.httpRequest.HttpRequestBody;
import webserver.http.httpRequest.HttpRequestHeader;
import webserver.http.httpRequest.HttpStartLine;

import static org.assertj.core.api.Assertions.assertThat;

class UrlMapperTest {
    String getStartLine = "GET /index.html HTTP1.1";
    String body = "name=java&coach=brown";
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

    @Mock
    Controller mockController;

    @Test
    void 컨트롤러_가지_않는_요청_테스트() {
        UrlMapper urlMapper = new UrlMapper();
        HttpStartLine httpStartLine = HttpStartLine.of(getStartLine);
        HttpRequestHeader httpRequestHeader = HttpRequestHeader.of(header);
        HttpRequestBody httpRequestBody = HttpRequestBody.of(body);
        HttpRequest httpRequest = new HttpRequest(httpStartLine, httpRequestHeader, httpRequestBody);


        String expectedUrl = httpRequest.getPath();
        assertThat(urlMapper.service(httpRequest)).isEqualTo(expectedUrl);
    }
}