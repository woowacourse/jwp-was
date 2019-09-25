package http;

import controller.Controller;
import controller.ControllerFactory;
import http.request.Request;
import http.request.RequestInformation;
import http.request.RequestMethod;
import http.request.RequestUrl;
import http.response.Response;
import http.response.ResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {

    @Test
    @DisplayName("Get File 요청시 Response 확인")
    void getFileResponseTest() throws IOException, URISyntaxException {
        RequestMethod method = RequestMethod.GET;
        RequestUrl url = RequestUrl.from("/index.html");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "GET /index.html HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);
        Response response = new Response();

        ControllerFactory factory = new ControllerFactory();
        Controller controller = factory.mappingController(request);

        controller.processResponse(request, response);

        Map<String, String> confirmMap = new LinkedHashMap<>();
        confirmMap.put("Content-Type: ", "text/html");
        confirmMap.put("Content-Length: ", "7049");

        byte[] confirmBody = FileIoUtils.loadFileFromClasspath("../resources/templates/index.html");

        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.OK);
        assertThat(response.getResponseHeaders().getResponseHeaders()).isEqualTo(confirmMap);
        assertThat(response.getResponseBody().getBody()).isEqualTo(confirmBody);
    }

    @Test
    @DisplayName("Post user/create 요청시 Response 확인")
    void postResponseTest() throws IOException, URISyntaxException {
        RequestMethod method = RequestMethod.POST;
        RequestUrl url = RequestUrl.from("/user/create");
        Map<String, String> information = new HashMap<>();
        information.put("Request-Line:", "POST /user/create HTTP/1.1");
        information.put("Host:", "localhost:8080");
        information.put("Connection:", "keep-alive");
        RequestInformation requestInformation = new RequestInformation(information);

        Request request = new Request(method, url, requestInformation);
        Response response = new Response();

        ControllerFactory factory = new ControllerFactory();
        Controller controller = factory.mappingController(request);

        controller.processResponse(request, response);

        Map<String, String> confirmMap = new LinkedHashMap<>();
        confirmMap.put("Location: ", "http://localhost:8080/index.html");


        assertThat(response.getResponseStatus()).isEqualTo(ResponseStatus.FOUND);
        assertThat(response.getResponseHeaders().getResponseHeaders()).isEqualTo(confirmMap);
    }
}
