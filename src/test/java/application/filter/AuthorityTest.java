package application.filter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import application.filter.auth.Authority;
import application.filter.auth.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import session.Session;
import session.SessionStorage;

/**
 * 권한 있는 사용자 접근시
 * validateAuthority 메서드가 void 를 return 하는데
 * 이를 테스트할 방법을 못찾아서 findAllUsers 테스트에서 통합테스트하고 있습니다.
 */
class AuthorityTest {

    @Test
    @DisplayName("권한 없는 사용자 접근 차단")
    void validateAuthority() {
        // given
        Session session = SessionStorage.createSession();

        // when & then
        assertThatThrownBy(() -> Authority.validateAuthority(session))
            .isInstanceOf(UnauthorizedException.class);
    }
}