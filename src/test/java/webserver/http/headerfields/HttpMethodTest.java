package webserver.http.headerfields;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpMethodTest {
    @Test
    @DisplayName("HttpMethod 모든 메소드를 찾는다.")
    void findHttpMethod() {
        HttpMethod[] httpMethods = HttpMethod.values();

        for (HttpMethod httpMethod : httpMethods) {
            String methodName = httpMethod.name();
            assertThat(HttpMethod.of(methodName).get()).isEqualTo(httpMethod);
        }
    }
}