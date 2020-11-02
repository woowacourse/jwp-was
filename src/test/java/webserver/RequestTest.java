package webserver;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

public class RequestTest {

    @Test
    void validRequest() {
        String input = "T / HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36\n"
            + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n"
            + "Sec-Fetch-Site: none\n"
            + "Sec-Fetch-Mode: navigate\n"
            + "Sec-Fetch-User: ?1\n"
            + "Sec-Fetch-Dest: document\n"
            + "Accept-Encoding: gzip, deflate, br\n"
            + "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\n"
            + "Cookie: Idea-fa4c24af=ad141e17-4aac-4d14-9734-e7b2f061d3c6; _ga=GA1.1.790840572.1597740932; adminer_version=0; adminer_permanent=c2VydmVy-ZGI%3D-cm9vdA%3D%3D-%3AssCnfWohWNs%3D";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Request request = new Request(inputStream);
        assertThat(request).hasToString(input);
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void invalidRequest(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Request request = new Request(inputStream);
        assertThat(request).hasToString("");
    }
}
