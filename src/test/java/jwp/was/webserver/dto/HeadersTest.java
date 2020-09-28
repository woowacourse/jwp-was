package jwp.was.webserver.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static util.Constants.EMPTY;

import com.google.common.net.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeadersTest {

    private static final String HEADER_DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "3";
    private static final String CONTENT_TYPE = "application/json";

    @DisplayName("정적 팩터리 메서드")
    @Test
    void from_Success() {
        List<String> headerLists = new ArrayList<>();
        headerLists.add(HttpHeaders.CONTENT_LENGTH + HEADER_DELIMITER + CONTENT_LENGTH);
        headerLists.add(HttpHeaders.CONTENT_TYPE + HEADER_DELIMITER + CONTENT_TYPE);

        Headers headers = Headers.from(headerLists);

        Map<String, String> headersDetail = headers.getHeaders();
        assertThat(headersDetail).hasSize(2);
        assertThat(headersDetail.get(HttpHeaders.CONTENT_LENGTH)).isEqualTo(CONTENT_LENGTH);
        assertThat(headersDetail.get(HttpHeaders.CONTENT_TYPE)).isEqualTo(CONTENT_TYPE);
    }

    @DisplayName("정적 팩터리 메서드, 헤더 값이 없는 경우 EMPTY 치환")
    @Test
    void from_NotExistsHeaderValue_TurnEmpty() {
        List<String> headerLists = new ArrayList<>();
        headerLists.add(HttpHeaders.CONTENT_LENGTH + HEADER_DELIMITER + CONTENT_LENGTH);
        headerLists.add(HttpHeaders.CONTENT_TYPE + HEADER_DELIMITER);

        Headers headers = Headers.from(headerLists);

        Map<String, String> headersDetail = headers.getHeaders();
        assertThat(headersDetail).hasSize(2);
        assertThat(headersDetail.get(HttpHeaders.CONTENT_LENGTH)).isEqualTo(CONTENT_LENGTH);
        assertThat(headersDetail.get(HttpHeaders.CONTENT_TYPE)).isEqualTo(EMPTY);
    }

    @DisplayName("정적 팩터리 메서드, 헤더 값이 없는 경우 EMPTY 치환")
    @Test
    void from_DuplicateHeaderKey_UseFirstValue() {
        List<String> headerLists = new ArrayList<>();
        headerLists.add(HttpHeaders.CONTENT_LENGTH + HEADER_DELIMITER + CONTENT_LENGTH);
        headerLists.add(HttpHeaders.CONTENT_LENGTH + HEADER_DELIMITER + CONTENT_TYPE);

        Headers headers = Headers.from(headerLists);

        Map<String, String> headersDetail = headers.getHeaders();
        assertThat(headersDetail).hasSize(1);
        assertThat(headersDetail.get(HttpHeaders.CONTENT_LENGTH)).isEqualTo(CONTENT_LENGTH);
    }

    @DisplayName("정적 팩터리 메서드, 헤더 키/값 구분자가 없는 경우 DROP")
    @Test
    void from_NotExistsHeaderKeyValueDelimiter_Drop() {
        List<String> headerLists = new ArrayList<>();
        headerLists.add(HttpHeaders.CONTENT_LENGTH);
        headerLists.add(HttpHeaders.CONTENT_LENGTH);

        Headers headers = Headers.from(headerLists);

        Map<String, String> headersDetail = headers.getHeaders();
        assertThat(headersDetail).hasSize(0);
    }

    @DisplayName("존재하는 헤더 값 가져오기")
    @Test
    void get_ExistsHeader_ReturnValue() {
        List<String> headerLists = new ArrayList<>();
        headerLists.add(HttpHeaders.CONTENT_LENGTH + HEADER_DELIMITER + CONTENT_LENGTH);

        Headers headers = Headers.from(headerLists);
        assertThat(headers.get(HttpHeaders.CONTENT_LENGTH)).isEqualTo(CONTENT_LENGTH);
    }

    @DisplayName("존재하지 않는 헤더 값 가져오기, null 반환")
    @Test
    void get_ExistsHeader_Success() {
        List<String> headerLists = new ArrayList<>();
        headerLists.add(HttpHeaders.CONTENT_LENGTH + HEADER_DELIMITER + CONTENT_LENGTH);

        Headers headers = Headers.from(headerLists);
        assertThat(headers.get(HttpHeaders.CONTENT_TYPE)).isNull();
    }
}
