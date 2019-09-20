package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.headerfields.HttpStatusCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpStatusCodeTest {
    @Test
    @DisplayName("존재하는 상태코드를 찾는다.")
    void findHttpStatusCode() {
        assertThat(HttpStatusCode.of(200).get()).isEqualTo(HttpStatusCode.OK);
    }

    @Test
    @DisplayName("존재하지 않는 상태코드를 ")
    void notFindHttpStatusCode() {
        assertThrows(IllegalArgumentException.class, () ->
                HttpStatusCode.of(199).orElseThrow(IllegalArgumentException::new)
        );
    }
}