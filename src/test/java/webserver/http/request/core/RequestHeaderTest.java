package webserver.http.request.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.core.RequestHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.ParseString.parseHeaderData;
import static utils.UtilData.LINE;

class RequestHeaderTest {
    private Map<String, String> headers = new HashMap<>();
    private List<String> lines;
    private RequestHeader requestHeader;

    @BeforeEach
    void setUp() {
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "*/*");
    }

    @Test
    @DisplayName("Request Headers 가 잘 파싱되는지 테스트 한다.")
    void parseHeader() {
        lines = parseHeaderData(LINE);
        requestHeader = new RequestHeader(lines);
        assertThat(requestHeader.getHeadersKey("Host")).isEqualTo("localhost:8080");
        assertThat(requestHeader.getHeadersKey("Connection")).isEqualTo("keep-alive");
        assertThat(requestHeader.getHeadersKey("Accept")).isEqualTo("*/*");
    }


    @AfterEach
    void tearDown() {
        headers.clear();
    }
}