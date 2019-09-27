package servlet.controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class UserCreateControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateControllerTest.class);

    @Test
    @DisplayName("/user/create에 대한 POST 요청 테스트")
    public void doPost() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Controller controller = new UserCreateController();
        HttpRequest httpRequest = HttpRequestFactory.create(Common.getBufferedReaderOfText("HTTP_POST_USER_CREATE.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info(byteArrayOutputStream.toString());
    }
}