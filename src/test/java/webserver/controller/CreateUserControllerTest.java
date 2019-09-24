package webserver.controller;

import http.HttpRequest;
import http.HttpRequestBody;
import http.HttpRequestHeader;
import http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class CreateUserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserControllerTest.class);
    @Test
    @DisplayName("/user/create에 대한 POST 요청 테스트")
    public void doPost() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<String> inputs = Arrays.asList(
                "POST /user/create HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 46",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*");
        String body = "userId=javajigi&password=password";

        Controller controller = new CreateUserController();
        HttpRequest httpRequest = new HttpRequest(new HttpRequestHeader(inputs), new HttpRequestBody(body));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info(byteArrayOutputStream.toString());
    }
}