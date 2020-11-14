package http;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParametersTest {
    @Test
    @DisplayName("parameter 파싱 테스트")
    void parseTest() {
        String input = "userId=orange&password=password&name=Yerin";
        Parameters result = Parameters.parse(input);
        assertAll(() -> {
            assertEquals("orange", result.get("userId"));
            assertEquals("password", result.get("password"));
            assertEquals("Yerin", result.get("name"));
        });
    }

}
