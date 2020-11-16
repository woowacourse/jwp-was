package kr.wootecat.dongle.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomIdGeneratorTest {

    @DisplayName("랜덤 ID 생성기를 통해 생성한 값이 문자열이다. 값은 랜덤값으로 생성되기 때문에 타입만 확인한다.")
    @Test
    void create() {
        IdGenerator idGenerator = new RandomIdGenerator();
        String actual = idGenerator.create();
        assertThat(actual).isInstanceOf(String.class);
    }
}