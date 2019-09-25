package http.request.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        requestHeader.getHeaders().keySet()
                .forEach(key -> assertThat(requestHeader.getHeaders().get(key)).isEqualTo(headers.get(key)));
    }


    @AfterEach
    void tearDown() {
        headers.clear();
    }
}