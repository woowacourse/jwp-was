package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ResponseBodyTest {

    @DisplayName("ResponseBody 생성자 성공")
    @Test
    void constructor() {
        String input = "HTTP/1.1 200";
        assertThat(new ResponseBody(input.getBytes())).isNotNull();
    }
}
