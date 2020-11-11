package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringUtilsTest {
    @Test
    @DisplayName("한 줄의 입력이 들어왔을 때, 입력 다음 공백줄 제거")
    void deleteLastEmptyLineTest() {
        String input = "input contains empty line\n";
        String expected = "input contains empty line";
        assertEquals(expected, StringUtils.deleteLastEmptyLine(input));
    }

}
