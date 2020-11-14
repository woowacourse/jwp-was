package application.filter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

class AuthorityFilterTest {

    @Test
    void validateAuthority() throws IOException {
        // given
        String httpRequestFormat = "GET /join?id=1 HTTP/1.1\n"
            + "Host: localhost:8080\n"
            + "Connection: keep-alive\n"
            + "Cache-Control: max-age=0\n"
            + "Upgrade-Insecure-Requests: 1\n"
            + "Content-Length: 10\n\n";
        InputStream testInput = new ByteArrayInputStream(httpRequestFormat.getBytes());

        HttpRequest httpRequest = HttpRequest.readHttpRequest(testInput);

        // when & then
        assertThatThrownBy(() -> AuthorityFilter.validateAuthority(httpRequest))
            .isInstanceOf(UnauthorizedException.class);
    }
}