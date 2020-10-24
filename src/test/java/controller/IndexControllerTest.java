package controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.*;

import static controller.AbstractControllerTest.TEST_DIRECTORY;

public class IndexControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(IndexControllerTest.class);

    @DisplayName("GET으로 IndexController에 요청시, index.html 페이지가 반환된다.")
    @Test
    void serviceIndexControllerGetTest() {
        Controller controller = new IndexController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_INDEX.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            controller.service(request, response);

            //올바른 response가 왔는지 확인한다
            String result = outputStream.toString();

            Assertions.assertThat(result).contains("HTTP/1.1 200 OK");
            Assertions.assertThat(result).contains("Content-Type: text/html;charset=UTF-8");
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
