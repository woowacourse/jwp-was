package controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ControllerTypeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "/index.html,INDEX",
            "/user/form.html, FORM",
            "/user/create,USER",
            "css/style.css,NONE"})
    void name(String uri, ControllerType expected) {
        ControllerType actual = ControllerType.find(uri);
        assertThat(actual).isEqualTo(expected);
    }
}