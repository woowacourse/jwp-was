package web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import web.http.HttpMethod;

class HttpMethodTest {

    @DisplayName("find value")
    @Test
    public void findValue() {
        String input = "OPTION";

        HttpMethod result = HttpMethod.from(input);

        assertThat(result).isEqualTo(HttpMethod.NONE);
    }

}