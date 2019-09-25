package http;

import controller.ControllerMapper;
import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest {

    @Test
    @DisplayName("ControllerMapper 제대로 생성하는지 테스트")
    void createControllerMapper() {
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /index.html HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        RequestInformation requestInformation = new RequestInformation(information);
        RequestUrl url = requestInformation.extractUrl();

        Request request = new Request(RequestMethod.GET, url, requestInformation);

        assertThat(request.createControllerMapper()).isEqualTo(new ControllerMapper(RequestMethod.GET, "/index.html"));
    }
}
