package web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.controller.Controller;
import client.controller.UserCreateController;
import web.request.HttpRequest;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlerMappingTest {

    @DisplayName("요청에 맞는 servlet을 찾아준다.")
    @Test
    void find() throws IOException {
        InputStream inputStream = new FileInputStream("./src/test/resources/UserCreateRequest.txt");
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(inputStream)));

        Controller controller = HandlerMapping.find(httpRequest);

        assertThat(controller).isInstanceOf(UserCreateController.class);
    }

    @DisplayName("요청에 맞는 servlet이 존재하지 않으면 exception 발생")
    @Test
    void findException() throws IOException {
        InputStream inputStream = new FileInputStream("./src/test/resources/RequestWithoutServlet.txt");
        HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(inputStream)));

        assertThatThrownBy(() -> HandlerMapping.find(httpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청(%s: %s)을 처리할 수 없습니다.", httpRequest.getMethod(), httpRequest.getRequestPath());
    }
}