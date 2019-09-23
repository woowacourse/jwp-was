package controller;

import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import http.response.FileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FileControllerTest {

    @Test
    @DisplayName("Get File 요청시 제대로된 response가 나오는지 테스트")
    void createGetFileResponse() {
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/index.html");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /index.html HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);
        ControllerFactory factory = new ControllerFactory();
        Controller controller = factory.createController(request);

        assertThat(controller.createResponse(request).getClass()).isEqualTo(FileResponse.class);
    }
}
