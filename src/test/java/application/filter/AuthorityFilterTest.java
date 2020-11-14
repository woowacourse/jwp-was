package application.filter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import application.filter.auth.AuthorityFilter;
import application.filter.auth.UnauthorizedException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import request.HttpRequest;

/**
 * 권한 있는 사용자 접근시
 * validateAuthority 메서드가 void 를 return 하는데
 * 이를 테스트할 방법을 못찾아서 findAllUsers 테스트에서 통합테스트하고 있습니다.
 */
class AuthorityFilterTest {

    @Test
    @DisplayName("권한 없는 사용자 접근 차단")
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