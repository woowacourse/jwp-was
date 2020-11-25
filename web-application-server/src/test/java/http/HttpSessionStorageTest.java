package http;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HttpSessionStorageTest {

    @Test
    void isExist_True() {
        final HttpSession httpSession = new HttpSession();
        HttpSessionStorage.save(httpSession.getId(), httpSession);

        assertThat(HttpSessionStorage.isExistById(httpSession.getId())).isTrue();
    }

    @Test
    void isExist_False() {
        assertThat(HttpSessionStorage.isExistById("notExistId")).isFalse();
    }
}