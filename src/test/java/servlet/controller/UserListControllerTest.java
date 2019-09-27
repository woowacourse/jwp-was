package servlet.controller;

import http.response.HttpResponse;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static testhelper.Common.getBufferedReaderOfTextFile;

public class UserListControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserListControllerTest.class);

    @Test
    public void doGet() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_GET_USER_LIST_LOGIN.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        Controller controller = new UserListController();
        controller.service(httpRequest, httpResponse);

        logger.info(byteArrayOutputStream.toString());
    }

    @Test
    public void doGetWhenLogout() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_GET_USER_LIST_LOGOUT.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        Controller controller = new UserListController();
        controller.service(httpRequest, httpResponse);

        logger.info(byteArrayOutputStream.toString());
    }
}