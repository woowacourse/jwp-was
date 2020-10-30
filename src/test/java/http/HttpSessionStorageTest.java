package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HttpSessionStorageTest {

    @Test
    void isExist_True() {
        final HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();
        final HttpSession httpSession = new HttpSession();
        httpSessionStorage.save(httpSession.getId(), httpSession);

        assertThat(httpSessionStorage.isExistById(httpSession.getId())).isTrue();
    }

    @Test
    void isExist_False() {
        final HttpSessionStorage httpSessionStorage = HttpSessionStorage.getInstance();

        assertThat(httpSessionStorage.isExistById("notExistId")).isFalse();
    }
}