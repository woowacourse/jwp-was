package http.session;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static testhelper.Common.getBufferedReaderOfTextFile;

public class SessionManagerTest {
    private static final String SESSION_NAME = "SESSIONID";

    private SessionManager sessionManager;

    @BeforeEach
    public void setUp() {
        sessionManager = new SessionManager();
    }

    @Test
    @DisplayName("세션이 없을 때 새로운 세션을 발급한다")
    public void getNewSession() throws IOException {
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_GET_INDEX_HTML.txt"));

        HttpSession httpSession1 = sessionManager.getSession(httpRequest);
        HttpSession httpSession2 = sessionManager.getSession(httpRequest);

        assertThat(httpSession1).isNotEqualTo(httpSession2);
    }
}