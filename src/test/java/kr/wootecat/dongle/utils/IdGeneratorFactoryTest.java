package kr.wootecat.dongle.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.wootecat.dongle.model.http.session.IdGenerator;
import kr.wootecat.dongle.model.http.session.IdGeneratorFactory;

class IdGeneratorFactoryTest {

    @DisplayName("IdGenerator factory를 통해 IdGenerator 타입의 인스턴스를 생성한다.")
    @Test
    void create() {
        assertThat(IdGeneratorFactory.create())
                .isInstanceOf(IdGenerator.class);
    }
}