package http.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static utils.StringUtils.BLANK;

class SessionRepositoryTest {
    private SessionRepository sessionRepository = SessionRepository.getInstance();

    @Test
    void getSessionTest() {
        Session existSession = sessionRepository.getSession(BLANK);

        assertThat(existSession).isEqualTo(sessionRepository.getSession(existSession.getId()));
    }
}