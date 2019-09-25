package webserver.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.request.HttpVersion;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.HttpVersion.*;
import static webserver.response.ResponseStatus.*;

class HttpResponseTest {

    private HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        httpResponse = new HttpResponse(OK, new ResponseHeaders(), new ResponseBody("/index.html"));
    }

    @Test
    void sendErrorResponse() {
        assertThat(HttpResponse.notFound().getResponseStatus()).isEqualTo(NOT_FOUND);
        assertThat(HttpResponse.internalServerError().getResponseStatus()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(HttpResponse.methodNotAllowed().getResponseStatus()).isEqualTo(METHOD_NOT_ALLOWED);
    }

    @Test
    void forward() {
        String filePath = "/index.html";
        HttpResponse response = new HttpResponse(HTTP_1_1);
        response.forward(filePath);

        assertThat(response.getViewPath()).isEqualTo(new ResponseBody(filePath).getPath());
    }


    @Test
    void setContentType() {
        String contentType = "text/html";
        HttpResponse response = new HttpResponse(HTTP_1_1);

        response.setContentType(contentType);
        assertThat(response.getHeader("Content-Type")).isEqualTo(contentType);

        response.setContentType(MediaType.TEXT_HTML_VALUE);
        assertThat(response.getHeader("Content-Type")).isEqualTo(contentType);
    }

    @Test
    void sendRedirect() {
        HttpResponse response = new HttpResponse(HTTP_1_1);
        response.sendRedirect("/");

        assertThat(response.getHeader("Location")).isEqualTo("/");
        assertThat(response.getResponseStatus()).isEqualTo(FOUND);
    }

    @Test
    void responseBuilder() {
        HttpResponse response = new HttpResponse(HTTP_1_1);
        response.sendRedirect("/");

        List<String> parsedResponse = Arrays.asList(
                "HTTP/1.1 302 FOUND\r\n",
                "Location: /\r\n",
                "\r\n");

        assertThat(response.responseBuilder()).isEqualTo(parsedResponse);
    }
}