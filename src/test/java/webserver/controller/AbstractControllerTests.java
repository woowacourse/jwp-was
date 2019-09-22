package webserver.controller;

import exception.ContentTypeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import webserver.controller.request.HttpRequest;
import webserver.controller.request.RequestMapper;
import webserver.controller.response.ContentType;
import webserver.controller.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static exception.ContentTypeNotFoundException.CONTENT_TYPE_NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AbstractControllerTests {
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    RequestMapper requestMapper;

    @BeforeEach
    void setUp() {
        httpRequest = mock(HttpRequest.class);
        httpResponse = mock(HttpResponse.class);
        requestMapper = new RequestMapper();
    }

    @Test
    @DisplayName("올바르지 않은 ContentType으로 요청을 보냈을 때 MovePage의 결과")
    void invalid_Content_Type_movePage() {
        when(httpRequest.getContentType()).thenThrow(new ContentTypeNotFoundException());
        when(httpRequest.getPath()).thenReturn("/index.html");

        requestMapper.executeMapping(httpRequest, httpResponse);

        verify(httpResponse).badRequest(CONTENT_TYPE_NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("정상 .html 요청")
    void movePage() throws IOException, URISyntaxException {
        when(httpRequest.getContentType()).thenReturn(ContentType.HTML);
        when(httpRequest.getPath()).thenReturn("/index.html");
        byte[] bytes = "<html></html>".getBytes();
        when(httpRequest.getResponseBody(ContentType.HTML)).thenReturn("<html></html>".getBytes());
        requestMapper.executeMapping(httpRequest, httpResponse);

        verify(httpResponse).response200Header(bytes.length, ContentType.HTML);
        verify(httpResponse).responseBody(bytes);
    }
}
