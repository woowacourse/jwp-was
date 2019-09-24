package http.session;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpSessionManagerTest {
    private HttpSessionManager httpSessionManager = new HttpSessionManager(new RandomGenerateStrategy());

    @Test
    void 고유_세션_아이디인지() {
        Set<HttpSession> httpSessions = new HashSet<>();
        for (int i = 0; i < 100000; i++) {
            httpSessions.add(httpSessionManager.newSession());
        }

        assertThat(httpSessions.size()).isEqualTo(100000);
    }

    @Test
    void 세션_조작() {
        HttpSession httpSession = httpSessionManager.newSession();

        String id = httpSession.getId();
        httpSession.setAttribute("1+1", 2);

        assertThat(httpSessionManager.getSession(id).getAttribute("1+1")).isEqualTo(2);
    }

    @Test
    void ID_생성_전략_교체() {
        HttpSessionManager httpSessionManager = new HttpSessionManager(() -> {
            Random random = new Random();
            return String.valueOf(random.nextLong());

        });

        assertThat(httpSessionManager.newSession().getId()).isNotNull();
    }
}