package webserver.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionTest {
    private static final String NAME = "name";
    private static final String VALUE = "value";

    @Test
    void getOrMakeNewSession() {
        final HttpSession session = HttpSession.get("1a2b");
        assertThat(session).hasFieldOrProperty("uuid");
    }

    @Test
    void attribute() {
        final HttpSession session = HttpSession.makeNew();
        session.setAttribute(NAME, VALUE);
        assertThat(session.getAttribute(NAME)).isEqualTo(VALUE);
    }

    @Test
    void invalidate() {
        final HttpSession session = HttpSession.makeNew();
        session.setAttribute(NAME, VALUE);
        session.invalidate();
        assertThat(session.getAttribute(NAME)).isNull();
    }
}