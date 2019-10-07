package webserver.controller.response;

import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.controller.BasicTests;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.MimeType;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static webserver.ModelAndView.NON_STATIC_FILE_PATH;
import static webserver.controller.response.HttpResponse.STATIC_FILE_PATH;

public class HttpResponseTests extends BasicTests {
    private static final String TEST_MESSAGE = "올바르지 않은 요청입니다.";

    @Test
    void ok() {
        HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.getMimeType()).thenReturn(MimeType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("." + NON_STATIC_FILE_PATH + "/index.html").get();
        HttpResponse httpResponse = HttpResponse.ok(httpRequest, body);
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @Test
    void staticFile_ok() {
        HttpRequest httpRequest = mock(HttpRequest.class);
        when(httpRequest.getMimeType()).thenReturn(MimeType.CSS);
        byte[] body = FileIoUtils.loadFileFromClasspath(STATIC_FILE_PATH + "/css/bootstrap.min.css").get();
        HttpResponse httpResponse = HttpResponse.ok(httpRequest, body);
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo(body);
    }

    @Test
    void badRequest() {
        HttpResponse httpResponse = HttpResponse.badRequest(TEST_MESSAGE);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();

        assertThat(responseHeaderFields.get("Connection")).isEqualTo("close");
        assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseHeaderFields.get("message")).isEqualTo(TEST_MESSAGE);
    }
}
