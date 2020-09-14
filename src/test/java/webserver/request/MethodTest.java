package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import request.Method;

class MethodTest {

    @Test
    void from() {
        assertThat(Method.from("GET")).isEqualTo(Method.GET);
        assertThat(Method.from("POST")).isEqualTo(Method.POST);
        assertThat(Method.from("PUT")).isEqualTo(Method.PUT);
        assertThat(Method.from("DELETE")).isEqualTo(Method.DELETE);
    }

    @Test
    void from_IfNotExistMethod_ThrowException() {
        assertThatThrownBy(() -> Method.from("gget"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("this HTTP method does not exist.");
    }

    @Test
    void getMethod() {
        assertThat(Method.GET.getMethod()).isEqualTo("GET");
        assertThat(Method.POST.getMethod()).isEqualTo("POST");
        assertThat(Method.PUT.getMethod()).isEqualTo("PUT");
        assertThat(Method.DELETE.getMethod()).isEqualTo("DELETE");
    }
}
