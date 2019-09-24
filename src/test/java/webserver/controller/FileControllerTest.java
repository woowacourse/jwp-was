package webserver.controller;

import http.HttpRequest;
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

public class FileControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(FileControllerTest.class);

    @Test
    @DisplayName("정적 파일에 대한 파일을 리턴한다")
    public void doGetStaticResource() throws IOException, URISyntaxException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<String> inputs = Arrays.asList(
                "GET / HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive");

        Controller controller = new FileController();
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader(inputs);
        HttpRequest httpRequest = new HttpRequest(httpRequestHeader);
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);
        controller.service(httpRequest, httpResponse);

        logger.info(byteArrayOutputStream.toString());
    }
}