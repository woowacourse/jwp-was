package kr.wootecat.dongle.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.application.RequestProcessor;
import kr.wootecat.dongle.application.RequestProcessorFactory;

class RequestProcessorFactoryTest {

    @DisplayName("create를 통해 생성하는 인스턴스는 RequestProcessor 타입이다.")
    @Test
    void create() {
        assertThat(RequestProcessorFactory.create())
                .isInstanceOf(RequestProcessor.class);
    }
}