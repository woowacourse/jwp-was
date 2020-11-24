package server.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.controller.TestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlerTest {

    @DisplayName("Controller를 구현하고 @RequestMapping을 사용한 클래스를 Handler로 생성한다.")
    @Test
    void of() {
        //given
        String target = "server.controller.TestController";

        //when
        Handler handler = Handler.of(target);

        //then
        assertThat(handler.getController()).isInstanceOf(TestController.class);
    }

    @DisplayName("존재하지 않는 클래스를 생성하려는 경우 Exception 발생")
    @Test
    void ofException() {
        String target = "server.controller.TestController123123";

        assertThatThrownBy(() -> Handler.of(target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%s : 클래스가 존재하지 않습니다.", target);
    }
}