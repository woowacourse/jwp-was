package response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HttpResponseTest {

    @Test
    void buildHeader() {
        HttpResponse response = new HttpResponse(StatusCode.FOUND, "/");
        String expected = "HTTP/1.1 302 Found \r\n"
            + "Location: /\r\n";

        assertThat(response.buildHeader()).isEqualTo(expected);
    }

    @Test
    void getBody() {
    }
}