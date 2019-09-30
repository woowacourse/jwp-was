package http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseFieldTest {

    @Test
    void updateTest() {
        HttpResponseField httpResponseField = new HttpResponseField("test", "1234");
        httpResponseField.updateValue("4321");

        assertThat(httpResponseField.getValue()).isEqualTo("4321");
    }
}