package webserver.controller.request.header;

import exception.ContentTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContentTypeTests {

    @DisplayName("컨텐츠타입에 있지않은 url 요청")
    @Test
    void invalid_contentType() {
        assertThrows(ContentTypeNotFoundException.class, () -> {
            ContentType.match("dasdasd.testData");
        });
    }
}
