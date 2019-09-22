package http.common;

import http.exception.InvalidHeaderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HeaderFieldsTest {
    private HeaderFields fields;

    @BeforeEach
    void setUp() {
        List<String> headerLines = Arrays.asList(
                "Content-Length: 13309",
                "Content-Type: text/html; charset=utf-8"
        );
        fields = new HeaderFields(headerLines);
    }

    @Test
    @DisplayName("생성자 null 테스트")
    void constructor_null() {
        assertThrows(InvalidHeaderException.class, () -> {
            new HeaderFields(null);
        });
    }

    @Test
    @DisplayName("생성 및 addHeader()테스트")
    void constructor_and_addHeader() {
        HeaderFields fieldsByConstructor = fields;

        HeaderFields fieldsByAddHeader = new HeaderFields(new ArrayList<>());
        fieldsByAddHeader.addHeader("Content-Length", "13309");
        fieldsByAddHeader.addHeader("Content-Type", "text/html; charset=utf-8");

        assertThat(fieldsByConstructor.getHeader("Content-Length"))
                .isEqualTo(fieldsByAddHeader.getHeader("Content-Length"));
        assertThat(fieldsByConstructor.getHeader("Content-Type"))
                .isEqualTo(fieldsByAddHeader.getHeader("Content-Type"));
    }

    @Test
    @DisplayName("getHeader() 테스트")
    void getHeader() {
        assertThat(fields.getHeader("Content-Type")).isEqualTo("text/html; charset=utf-8");
    }

    @Test
    @DisplayName("존재하지 않는 헤더 필드 테스트")
    void getHeader_notExist() {
        assertThrows(InvalidHeaderException.class, () -> {
            fields.getHeader("Foo-Header");
        });
    }

    @Test
    @DisplayName("getcontentLength() 테스트")
    void getContentLength() {
        assertThat(fields.getContentLength()).isEqualTo(13309);
    }

    @Test
    @DisplayName("contentLength가 필드에 존재하지 않는 경우 0을 리턴하는지 확인")
    void getContentLength_is_0_if_notExist() {
        HeaderFields fieldsWithoutContentLength = new HeaderFields(new ArrayList<>());
        assertThat(fieldsWithoutContentLength.getContentLength()).isEqualTo(0);
    }

    @Test
    void testToString() {
        String stringToCompare =
                "Content-Length: 13309\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n";
        assertThat(fields.toString()).isEqualTo(stringToCompare);
    }
}