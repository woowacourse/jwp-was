package webserver.httpelement;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderFieldTest {
    @Test
    void getFieldNameTest() {
        assertThat(HttpHeaderField.getName(HttpConnection.class)).isEqualTo("Connection");
        assertThat(HttpHeaderField.getName(HttpHost.class)).isEqualTo("Host");
        assertThat(HttpHeaderField.getName(HttpContentType.class)).isEqualTo("Content-Type");
    }

    @Test
    void fieldNameFromInstanceTest() {
        assertThat(HttpConnection.KEEP_ALIVE.fieldName()).isEqualTo("Connection");
        assertThat(HttpHost.of("localhost:8080").get().fieldName()).isEqualTo("Host");
        assertThat(HttpContentType.TEXT_HTML_UTF_8.fieldName()).isEqualTo("Content-Type");
    }
}