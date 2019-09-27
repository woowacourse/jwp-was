package servlet;

import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import http.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static testhelper.Common.getControllerFinder;

public class ServletContainerTest {
    private static final Logger logger = LoggerFactory.getLogger(ServletContainerTest.class);

    @Test
    @DisplayName("/user/create에 대한 HttpResponse를 반환한다")
    public void processCreateUser() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpRequest httpRequest = HttpRequestFactory.create(
                Common.getBufferedReaderOfText("HTTP_POST_USER_CREATE.txt"));
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        ServletContainer servletContainer = new ServletContainer(getControllerFinder());
        servletContainer.process(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }
}