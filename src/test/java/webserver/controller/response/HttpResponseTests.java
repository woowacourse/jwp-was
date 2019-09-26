package webserver.controller.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.MimeType;
import webserver.controller.request.header.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static exception.HttpMethodNotFoundException.HTTP_METHOD_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpResponseTests {
    HttpResponse httpResponse;
    HttpRequest httpRequest;

    @BeforeEach
    void setUp() {
        httpRequest = mock(HttpRequest.class);
        when(httpRequest.getVersion()).thenReturn("HTTP 1.1");
    }

    @Test
    void Ok() throws IOException, URISyntaxException {
        when(httpRequest.getMimeType()).thenReturn(MimeType.HTML);
        when(httpRequest.getPath()).thenReturn("index.html");
        when(httpRequest.getHttpMethod()).thenReturn(HttpMethod.GET);
        httpResponse = new HttpResponse(httpRequest);
        httpResponse.responseOK(httpRequest);

        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();
        assertThat(responseHeaderFields.get("Content-Type")).isEqualTo(MimeType.HTML.getMimeType() + ";charset=utf-8\r\n");
    }

    @Test
    void Redirect() throws IOException, URISyntaxException {
        String redirectUrl = "/index.html";
        when(httpRequest.getHttpMethod()).thenReturn(HttpMethod.POST);
        httpResponse = new HttpResponse(httpRequest);
        httpResponse.sendRedirect(redirectUrl);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();
        assertThat(responseHeaderFields.get("Location")).isEqualTo(redirectUrl);
        assertThat(responseHeaderFields.get("Connection")).isEqualTo("close");
    }

    @Test
    void BadRequest() throws IOException, URISyntaxException {
        when(httpRequest.getHttpMethod()).thenReturn(HttpMethod.POST);
        httpResponse = new HttpResponse(httpRequest);
        httpResponse.responseBadRequest(HTTP_METHOD_NOT_FOUND_MESSAGE);
        Map<String, String> responseHeaderFields = httpResponse.getHeaderFields();
        assertThat(responseHeaderFields.get("Server")).isEqualTo("jwp-was");
        assertThat(responseHeaderFields.get("Connection")).isEqualTo("close");
        assertThat(responseHeaderFields.get("message")).isEqualTo(HTTP_METHOD_NOT_FOUND_MESSAGE);
    }
}
