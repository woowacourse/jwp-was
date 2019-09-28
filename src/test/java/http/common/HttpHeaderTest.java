package http.common;

import http.common.exception.InvalidHeaderKeyException;
import http.common.exception.InvalidHttpHeaderException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.net.HttpHeaders.CONTENT_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.StringUtils.BLANK;

class HttpHeaderTest {
    @Test
    void header가_null일_때_빈_HttpHeader_생성() {
        assertThat(new HttpHeader(null)).isEqualTo(new HttpHeader(new ArrayList<>()));
    }

    @Test
    void 잘못된_생성_header_parameter가_null일_때() {
        List<String> header = new ArrayList<>();
        header.add("Content-type: text/html");
        header.add(null);
        assertThrows(InvalidHttpHeaderException.class, () -> new HttpHeader(header));
    }

    @Test
    void 잘못된_생성_header_parameter_형식이_잘못된_경우() {
        List<String> header = new ArrayList<>();
        header.add("Content-type");
        assertThrows(InvalidHttpHeaderException.class, () -> new HttpHeader(header));
    }

    @Test
    void header_필드_조회() {
        String contentType = "Content-type";
        String textHTML = "text/html";

        List<String> header = new ArrayList<>();
        header.add(String.format("%s: %s", contentType, textHTML));
        header.add(String.format("%s: ", CONTENT_LENGTH));
        HttpHeader httpHeader = new HttpHeader(header);

        assertThat(httpHeader.getHeaderAttribute(contentType)).isEqualTo(textHTML);
        assertThat(httpHeader.getHeaderAttribute(CONTENT_LENGTH)).isEqualTo(BLANK);
    }

    @Test
    void get_key가_null일_때() {
        HttpHeader httpHeader = new HttpHeader(Collections.emptyList());

        assertThrows(InvalidHeaderKeyException.class, () -> httpHeader.getHeaderAttribute(null));
    }
}