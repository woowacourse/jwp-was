package http.common;

import http.common.exception.InvalidContentTypeException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentTypeMapperTest {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", ".", "a.b"})
    void 잘못된_확장자(String path) {
        assertThrows(InvalidContentTypeException.class, () ->
                ContentTypeMapper.getContentType(path));
    }
}