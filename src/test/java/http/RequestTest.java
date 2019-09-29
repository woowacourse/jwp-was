package http;

import controller.controllermapper.ControllerMapper;
import http.request.Request;
import http.request.RequestMethod;
import http.request.RequestUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTest extends BaseTest {

    @Test
    @DisplayName("ControllerMapper 제대로 생성하는지 테스트")
    void createControllerMapper() {
        RequestUrl url = RequestUrl.from("/index.html");
        List<String> headerValues = Arrays.asList("GET /index.html HTTP/1.1");

        Request request = createGetRequest(url, headerValues);

        assertThat(request.createControllerMapper()).isEqualTo(new ControllerMapper(RequestMethod.GET, "/index.html"));
    }
}
