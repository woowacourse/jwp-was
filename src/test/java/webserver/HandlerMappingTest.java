package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HandlerMapping;
import webserver.request.Request;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class HandlerMappingTest {

    @DisplayName("Request에 맞는 HandlerMapping을 잘 찾아오는지")
    @Test
    void from() {
        String httpRequest = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpRequest));

        HandlerMapping handlerMapping = HandlerMapping.from(new Request(bufferedReader));

        assertThat(handlerMapping).isEqualTo(HandlerMapping.PAGE);
    }

    @DisplayName("Request와 일치하는 HadlerMapping이 없을 때 Exception 발생")
    @Test
    void from2() {
        String httpRequest = "GET /!@# HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        BufferedReader bufferedReader = new BufferedReader(new StringReader(httpRequest));

        assertThatThrownBy(() -> HandlerMapping.from(new Request(bufferedReader)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("일치하는 컨트롤러를 찾지 못했습니다.");
    }


}
