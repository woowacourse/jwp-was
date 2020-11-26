package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControllerManagerTest {

    private ControllerManager controllerManager;

    @BeforeEach
    void setUp() {
        List<Class> controllerInfos = new ControllerMapperForTest().readAllControllerInfo();
        controllerManager = new ControllerManager(controllerInfos);
        System.out.println("**");
    }

    @Test
    @DisplayName("get")
    void get() {
        assertThat(controllerManager.get(ControllerForTest.class))
            .isInstanceOf(ControllerForTest.class);
    }

    @Test
    @DisplayName("get - 정의된 컨트롤러 클래스가 아닌 것을 인수로 받았을 때 예외처리")
    void get_IfInputIsWrong_ThrowException() {
        assertThatThrownBy(() -> controllerManager.get(String.class))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
