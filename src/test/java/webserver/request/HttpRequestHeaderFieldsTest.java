package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestHeaderFieldsTest {

    private HttpRequestHeaderFields httpRequestHeaderFields;

    @BeforeEach
    void setup() {
        httpRequestHeaderFields = new HttpRequestHeaderFields();
    }

    @Test
    void addAndFindField() {
        String name = "Content-Type";
        String value = "text/html";

        assertThat(httpRequestHeaderFields.findField(name)).isNull();
        httpRequestHeaderFields.addField(name, value);
        assertThat(httpRequestHeaderFields.findField(name)).isEqualTo(value);
    }
}