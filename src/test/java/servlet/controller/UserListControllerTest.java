package servlet.controller;

import http.request.HttpRequest;
import http.request.support.HttpRequestFactory;
import http.response.HttpResponse;
import http.session.support.SessionManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static testhelper.Common.getBufferedReaderOfTextFile;

public class UserListControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserListControllerTest.class);
    private static SessionManager sessionManager = new SessionManager();

    @Test
    public void doGetWhenLogin() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_GET_USER_LIST_LOGIN.txt"), sessionManager);
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        httpRequest.addSessionAttribute("logined", "true");

        Controller controller = new UserListController();
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
        BufferedReader bufferedReader = Common.convertToBufferedReader(byteArrayOutputStream);
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 200 OK");
    }

    @Test
    public void doGetWhenLogout() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpRequest httpRequest = HttpRequestFactory.create(
                getBufferedReaderOfTextFile("HTTP_GET_USER_LIST_LOGOUT.txt"), sessionManager);
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        Controller controller = new UserListController();
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
        BufferedReader bufferedReader = Common.convertToBufferedReader(byteArrayOutputStream);
        assertThat(bufferedReader.readLine()).isEqualTo("HTTP/1.1 302 FOUND");
        assertThat(bufferedReader.readLine()).isEqualTo("Location: /index.html");
    }
}