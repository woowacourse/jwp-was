package view;

import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.BLANK;

class TemplateViewTest {
    private TemplateEngineManager testTemplateManager = (filePath, model) ->
            String.format("%s : %s", filePath, model.toString()).getBytes();
    @Test
    void renderTest() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET / HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        String filePath = "/test";
        Map<String, Object> model = Collections.singletonMap("testkey", "testValue");

        TemplateView templateView = new TemplateView(
                testTemplateManager, filePath);
        templateView.render(model, httpResponse);

        byte[] expectBody = testTemplateManager.applyCompile(filePath, model);
        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.OK);
        assertEquals(new String(httpResponse.getBody()), new String(expectBody));
    }
}