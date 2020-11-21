package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import application.controller.UserController;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ControllerManagerTest {

    @ParameterizedTest
    @MethodSource("controllerClasses")
    @DisplayName("get")
    void get(Class<?> controllerClass) {
        assertThat(ControllerManager.get(controllerClass)).isInstanceOf(controllerClass);
    }

    private static Stream<Arguments> controllerClasses() {
        return Stream.of(
            Arguments.of(StaticFileController.class),
            Arguments.of(UserController.class)
        );
    }

    @Test
    @DisplayName("get - 정의된 컨트롤러 클래스가 아닌 것을 인수로 받았을 때 예외처리")
    void get_IfInputIsWrong_ThrowException() {
        assertThatThrownBy(() -> ControllerManager.get(String.class))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
