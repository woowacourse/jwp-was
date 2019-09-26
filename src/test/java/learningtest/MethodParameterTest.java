package learningtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodParameterTest {

    @Test
    @DisplayName("String 이 함수의 파라미터로 넘겨졌을때(그리고 해당 함수에서 변경이 되었을 때) 변경이 되는지")
    void _() {
        String input = "origin";
        changeString(input);


        assertThat(input).isEqualTo("origin");
    }

    private void changeString(String input) {
        input.replace('o', 'O');
    }

}
