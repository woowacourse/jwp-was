package webserver.support;

import org.junit.jupiter.api.Test;
import webserver.exception.InvalidContentTypeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentTypeHandlerTest {

    @Test
    public void type() {
        String contentType = "html";
        assertThat(ContentTypeHandler.type(contentType)).isEqualTo("text/html ;charset=utf-8");
    }

    @Test
    public void typeException() {
        String errorType = "none.none";
        assertThrows(InvalidContentTypeException.class, () -> ContentTypeHandler.type(errorType));
    }
}