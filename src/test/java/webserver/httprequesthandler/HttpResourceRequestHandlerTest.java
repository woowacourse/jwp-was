package webserver.httprequesthandler;

import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import http.common.HttpVersion;
import http.request.HttpRequest;
import http.request.RequestMethod;
import http.response.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class HttpResourceRequestHandlerTest {
    public static final String TEST_PATH = "testPath.html";
    public static final String TEST_BODY = "test";
    HttpResponse httpResponse;

    @Mock
    TemplateLoader templateLoader;

    @Mock
    HttpRequest httpRequest;

    @Mock
    TemplateSource templateSource;

    @InjectMocks
    HttpResourceRequestHandler httpResourceRequestHandler;

    @BeforeEach
    void setup() {
        httpResponse = new HttpResponse(HttpVersion.HTTP_1_1);
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a.html", "b.css", "c.png", "d.js"})
    @DisplayName("파일 요청 시 true 반환")
    void canHandleTrue(String input) {
        assertTrue(httpResourceRequestHandler.canHandle(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"/", "/user/form", "/user/login", "/user/create", "/user/list"})
    @DisplayName("파일 이외 요청 시 false 반환")
    void canHandleFalse(String input) {
        assertFalse(httpResourceRequestHandler.canHandle(input));
    }

    @Test
    @DisplayName("정적 자원 처리")
    void handleInternal() throws IOException {
        given(httpRequest.getPath()).willReturn(TEST_PATH);
        given(httpRequest.getMethod()).willReturn(RequestMethod.GET);
        given(templateLoader.sourceAt(TEST_PATH)).willReturn(templateSource);
        given(templateSource.content(Charset.defaultCharset())).willReturn(TEST_BODY);

        httpResourceRequestHandler.handleInternal(httpRequest, httpResponse);
        assertEquals(new String(httpResponse.getBody()), TEST_BODY);
    }
}