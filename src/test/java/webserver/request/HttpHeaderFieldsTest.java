package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeaderFieldsTest {

    private HttpHeaderFields httpHeaderFields;

    @BeforeEach
    void setup() {
        httpHeaderFields = new HttpHeaderFields();
    }

    @Test
    void addAndFindField() {
        String name = "Content-Type";
        String value = "text/html";

        assertThat(httpHeaderFields.findField(name)).isNull();
        httpHeaderFields.addField(name, value);
        assertThat(httpHeaderFields.findField(name)).isEqualTo(value);
    }
}