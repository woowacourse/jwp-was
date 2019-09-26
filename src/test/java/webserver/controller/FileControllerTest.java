package webserver.controller;

import http.HttpResponse;
import http.request.HttpRequest;
import http.request.HttpRequestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testhelper.Common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(FileControllerTest.class);

    @Test
    @DisplayName("CSS 파일을 Response로 전달한다")
    public void doGetCssResource() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        BufferedReader bufferedReader = Common.getBufferedReaderOfText("HTTP_GET_CSS.txt");
        HttpRequest httpRequest = HttpRequestFactory.create(bufferedReader);
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        Controller controller = new FileController();
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }

    @Test
    @DisplayName("HTML 파일을 Response로 전달한다")
    public void doGetHtmlResource() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        BufferedReader bufferedReader = Common.getBufferedReaderOfText("HTTP_GET_INDEX_HTML.txt");
        HttpRequest httpRequest = HttpRequestFactory.create(bufferedReader);
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        Controller controller = new FileController();
        controller.service(httpRequest, httpResponse);

        logger.info("\n" + byteArrayOutputStream.toString());
    }
}