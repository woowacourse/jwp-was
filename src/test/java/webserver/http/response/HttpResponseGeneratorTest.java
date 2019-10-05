package webserver.http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.view.ModelAndView;

import static webserver.http.response.HttpResponse.HEADER_RESPONSE_LOCATION;
import static webserver.http.response.HttpResponseGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseGeneratorTest {
    private String filePath = "/index.html";
    private ModelAndView modelAndView;

    @BeforeEach
    void setUp() {
        modelAndView = new ModelAndView(filePath);
    }

    @DisplayName("200 응답 헤더 확인")
    @Test
    void response200() {
        HttpResponse expectHeader = HttpResponseGenerator.response200Header(modelAndView);

        assertThat(expectHeader.getStatusLine().getElementValue(HTTP_VERSION)).isEqualTo("HTTP/1.1");
        assertThat(expectHeader.getStatusLine().getElementValue(STATUS_CODE)).isEqualTo("200");
        assertThat(expectHeader.getStatusLine().getElementValue(REASON_PHRASE)).isEqualTo("OK");

        assertThat(expectHeader.getHeader().get(HEADER_CONTENT_TYPE)).isEqualTo("text/html;charset=utf-8\r\n");
        assertThat(expectHeader.getHeader().get(HEADER_CONTENT_LENGTH)).isEqualTo("7094\r\n");
    }

    @DisplayName("302 응답 헤더 확인")
    @Test
    void response302() {
        HttpResponse expectHeader = HttpResponseGenerator.response302Header(modelAndView);
        assertThat(expectHeader.getStatusLine().getElementValue(HTTP_VERSION)).isEqualTo("HTTP/1.1");
        assertThat(expectHeader.getStatusLine().getElementValue(STATUS_CODE)).isEqualTo("302");
        assertThat(expectHeader.getStatusLine().getElementValue(REASON_PHRASE)).isEqualTo("Found");
        assertThat(expectHeader.getHeader().get(HEADER_RESPONSE_LOCATION)).isEqualTo(filePath);
    }
}