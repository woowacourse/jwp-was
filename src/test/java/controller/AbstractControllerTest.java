package controller;

import exception.RequestMethodNotSupportedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.request.HttpRequest;
import web.response.HttpResponse;

import java.io.*;

public class AbstractControllerTest {
    public static final String TEST_DIRECTORY = "./src/test/resources/controller/";
    private static final Logger logger = LoggerFactory.getLogger(AbstractControllerTest.class);

    @DisplayName("GET으로 AbstractController에 요청시, 400 Bad Request가 반환된다.")
    @Test
    void serviceUndefinedGetMethodTest() {
        TestController controller = new TestController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_INDEX.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            //예외가 발생하는지 확인한다.
            Assertions.assertThatThrownBy(() -> controller.callGetMethod(request, response))
                    .isInstanceOf(RequestMethodNotSupportedException.class)
                    .hasMessage("GET 요청을 지원하지 않습니다.");

            //올바른 response가 왔는지 확인한다
            String result = outputStream.toString();

            Assertions.assertThat(result).contains("HTTP/1.1 400 BAD REQUEST");
            Assertions.assertThat(result).contains("errorMessage : 지원하지 않는 GET 요청입니다.");
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }

    @DisplayName("POST로 AbstractController에 요청시, 400 Bad Request가 반환된다.")
    @Test
    void serviceUndefinedPostMethodTest() {
        TestController controller = new TestController();
        try {
            InputStream inputStream = new FileInputStream(new File(TEST_DIRECTORY + "HTTP_INDEX.txt"));
            HttpRequest request = new HttpRequest(inputStream);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            HttpResponse response = new HttpResponse(outputStream);

            //예외가 발생하는지 확인한다.
            Assertions.assertThatThrownBy(() -> controller.callPostMethod(request, response))
                    .isInstanceOf(RequestMethodNotSupportedException.class)
                    .hasMessage("POST 요청을 지원하지 않습니다.");

            //올바른 response가 왔는지 확인한다
            String result = outputStream.toString();

            Assertions.assertThat(result).contains("HTTP/1.1 400 BAD REQUEST");
            Assertions.assertThat(result).contains("errorMessage : 지원하지 않는 POST 요청입니다.");
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new AssertionError();
        }
    }
}
