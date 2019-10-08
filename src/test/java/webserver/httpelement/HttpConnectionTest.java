package webserver.httpelement;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpConnectionTest {
    @Test
    void initTest() {
        assertThat(HttpConnection.of("keeP-alive").get()).isEqualTo(HttpConnection.KEEP_ALIVE);
        assertThat(HttpConnection.of("cloSe").get()).isEqualTo(HttpConnection.CLOSE);
        assertThat(HttpConnection.of("upGrade").get()).isEqualTo(HttpConnection.UPGRADE);
    }

    @Test
    void toStringTest() {
        assertThat(HttpConnection.KEEP_ALIVE.toString()).isEqualTo("keep-alive");
        assertThat(HttpConnection.CLOSE.toString()).isEqualTo("close");
        assertThat(HttpConnection.UPGRADE.toString()).isEqualTo("upgrade");
    }
}