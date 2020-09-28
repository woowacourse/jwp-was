package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HandlerMapping;
import webserver.request.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class HandlerMappingTest {
    private String testDirectory = "./src/test/resources/";

    @DisplayName("Request에 맞는 HandlerMapping을 잘 찾아오는지")
    @Test
    void from() throws FileNotFoundException {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));

        HandlerMapping handlerMapping = HandlerMapping.from(new HttpRequest(in));

        assertThat(handlerMapping).isEqualTo(HandlerMapping.GET_USER_CREATE);
    }

    @DisplayName("Request와 일치하는 HadlerMapping이 없을 때 Exception 발생")
    @Test
    void from2() {
        String httpRequest = "GET /abcd HTTP/1.1\n" +
                "Host: localhost:8080\n";
        InputStream notMatched = new ByteArrayInputStream(httpRequest.getBytes());

        assertThatThrownBy(() -> HandlerMapping.from(new HttpRequest(notMatched)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("일치하는 컨트롤러를 찾지 못했습니다.");
    }


}
