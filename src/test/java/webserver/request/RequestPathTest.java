package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RequestPathTest {

    @DisplayName("getRequestPath 성공 - 루트 디렉토리의 경우 index.html")
    @ParameterizedTest
    @CsvSource({"/index.html,/index.html", "/,/index.html"})
    void getRequestPath(String input, String expected) {
        assertThat(new RequestPath(input).getRequestPath()).isEqualTo(expected);
    }
}
