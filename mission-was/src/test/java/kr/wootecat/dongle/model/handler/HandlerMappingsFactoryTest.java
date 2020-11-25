package kr.wootecat.dongle.model.handler;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerMappingsFactoryTest {

    @DisplayName("HandlerMappingsFactory를 통해서 생성한 인스턴스는  HandlerMappings 타입을 갖는다.")
    @Test
    void create() {
        assertThat(HandlerMappingsFactory.create()).isInstanceOf(HandlerMappings.class);
    }
}