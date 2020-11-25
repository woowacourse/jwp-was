package server.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import server.TestWebServerApplication;
import server.controller.TestController;
import server.web.controller.Controller;
import server.web.controller.HandlerMapping;
import server.web.request.HttpRequest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlerMappingTest {

    @DisplayName("요청에 맞는 servlet을 찾아준다.")
    @Test
    void find() throws IOException {
        InputStream inputStream = new FileInputStream("./src/test/resources/TestControllerRequest.txt");
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(inputStream)));

        HandlerMapping handlerMapping = new HandlerMapping(TestWebServerApplication.class);

        Controller controller = handlerMapping.find(httpRequest);

        assertThat(controller).isInstanceOf(TestController.class);
    }

    @DisplayName("요청에 맞는 servlet이 존재하지 않으면 exception 발생")
    @Test
    void findException() throws IOException {
        InputStream inputStream = new FileInputStream("./src/test/resources/RequestWithoutServlet.txt");
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(inputStream)));

        HandlerMapping handlerMapping = new HandlerMapping(TestWebServerApplication.class);

        assertThatThrownBy(() -> handlerMapping.find(httpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청(%s: %s)을 처리할 수 없습니다.", httpRequest.getMethod(), httpRequest.getRequestPath());
    }
}