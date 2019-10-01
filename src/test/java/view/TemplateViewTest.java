package view;

import http.common.HttpVersion;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

class TemplateViewTest {
    @Mock
    HttpRequest httpRequest;

    @Mock
    TemplateManager templateManager;

    @InjectMocks
    TemplateView templateView;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void render() {
        Map<String, Object> model = Collections.emptyMap();
        String testBody = "testBody";
        given(httpRequest.getSession()).willReturn(new Session(""));
        given(httpRequest.getHttpVersion()).willReturn(HttpVersion.HTTP_1_1);
        HttpResponse httpResponse = new HttpResponse(httpRequest);
        given(templateManager.applyCompile(null, model)).willReturn(testBody.getBytes());
        templateView.render(model, httpResponse);

        assertEquals(testBody, new String(httpResponse.getBody()));
    }
}