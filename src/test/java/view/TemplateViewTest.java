package view;

import http.common.HttpVersion;
import http.response.HttpResponse;
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
    HttpResponse httpResponse;

    @Mock
    TemplateManager templateManager;

    @InjectMocks
    TemplateView templateView;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        httpResponse = new HttpResponse(HttpVersion.HTTP_1_1);
    }

    @Test
    void render() {
        Map<String, Object> model = Collections.emptyMap();
        String testBody = "testBody";

        given(templateManager.applyCompile(null, model)).willReturn(testBody.getBytes());
        templateView.render(model, httpResponse);

        assertEquals(testBody, new String(httpResponse.getBody()));
    }
}