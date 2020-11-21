package db;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.HttpSession;

class SessionDataBaseTest {
    @DisplayName("HttpSession을 저장한다.")
    @Test
    void addHttpSession() {
        HttpSession httpSession = HttpSession.create();

        SessionDataBase.addHttpSession(httpSession);

        assertThat(SessionDataBase.findHttpSessionById(httpSession.getId())).isEqualTo(httpSession);
    }

    @DisplayName("id에 해당하는 HttpSession을 조회한다.")
    @Test
    void findHttpSessionById() {
        HttpSession firstHttpSession = HttpSession.create();
        HttpSession secondHttpSession = HttpSession.create();
        SessionDataBase.addHttpSession(firstHttpSession);
        SessionDataBase.addHttpSession(secondHttpSession);

        HttpSession firstPersist = SessionDataBase.findHttpSessionById(firstHttpSession.getId());
        HttpSession secondPersist = SessionDataBase.findHttpSessionById(firstHttpSession.getId());

        assertThat(firstPersist).isEqualTo(firstHttpSession);
        assertThat(secondPersist).isNotEqualTo(secondHttpSession);
    }
}