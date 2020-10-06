package webserver.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ContentTypeMapperTest {
    @DisplayName("파일 확장자에 맞는 ContentType을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"html:text/html;charset=utf-8", "css:text/css", "js:application/javascript"}, delimiter = ':')
    void map(String extension, String expected) {
        ContentType contentType = ContentTypeMapper.map(extension);

        assertThat(contentType.value()).isEqualTo(expected);
    }
}