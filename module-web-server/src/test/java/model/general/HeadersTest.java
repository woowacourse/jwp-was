package model.general;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HeadersTest {

    @Test
    @DisplayName("Headers 생성")
    void create() {
        Headers headers = new Headers();
        assertThat(headers).isInstanceOf(Headers.class);
    }

    @Test
    @DisplayName("Headers에 Header 추가")
    void addHeader() {
        Headers headers = new Headers();
        Header header = Header.CONTENT_TYPE;
        ContentType contentType = ContentType.HTML;

        headers.addHeader(header, contentType.getContentTypeValue());

        assertAll(
            () -> assertThat(headers.getHeaders()).isNotEmpty(),
            () -> assertThat(headers.getHeaders()).hasSize(1)
        );
    }

    @ParameterizedTest
    @DisplayName("Headers에 Header 존재 확인")
    @CsvSource(value = {"Content-Range:false", "Content-Type:true"}, delimiter = ':')
    void hasKey(String headerName, boolean expected) {
        Headers headers = new Headers();
        Header header = Header.CONTENT_TYPE;
        ContentType contentType = ContentType.HTML;

        headers.addHeader(header, contentType.getContentTypeValue());

        assertThat(headers.hasKey(Header.of(headerName))).isEqualTo(expected);
    }

    @Test
    @DisplayName("값 확인")
    void getValue() {
        Headers headers = new Headers();
        Header header = Header.CONTENT_TYPE;
        ContentType contentType = ContentType.HTML;

        headers.addHeader(header, contentType.getContentTypeValue());

        assertThat(headers.getValue(header)).isEqualTo(contentType.getContentTypeValue());
    }

    @Test
    @DisplayName("헤더 확인")
    void getHeaders() {
        Headers headers = new Headers();
        Header header = Header.CONTENT_TYPE;
        ContentType contentType = ContentType.HTML;

        headers.addHeader(header, contentType.getContentTypeValue());
        Map<Header, String> generatedHeaders = headers.getHeaders();

        assertAll(
            () -> assertThat(generatedHeaders).isInstanceOf(Map.class),
            () -> assertThat(generatedHeaders).hasSize(1),
            () -> assertThat(generatedHeaders).containsKey(header),
            () -> assertThat(generatedHeaders.get(header))
                .isEqualTo(contentType.getContentTypeValue())
        );
    }
}