package webserver.http.response;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseTest {
    @DisplayName("200상태의 HttpResponse를 생성한다.")
    @Test
    void of() {
        HttpResponse httpResponse = HttpResponse.ok("./templates/index.html")
            .build();

        assertThat(httpResponse).isNotNull();
    }

    @DisplayName("쿠키를 담은 HttpResponse를 생성한다.")
    @Test
    void setCookie() {
        HttpResponse httpResponse = HttpResponse.ok("./templates/index.html")
            .setCookie("logined","true", "/")
            .build();

        assertThat(httpResponse.getCookie()).isEqualTo("logined=true; Path=/");
    }
}