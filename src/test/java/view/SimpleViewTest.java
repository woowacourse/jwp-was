package view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleViewTest {
    @Test
    @DisplayName("String으로 body 생성")
    void stringToBody() {
        View view = new SimpleView("test");

        assertThat(view.render()).isEqualTo("test".getBytes());
    }
}
