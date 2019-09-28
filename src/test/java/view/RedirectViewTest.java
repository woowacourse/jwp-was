package view;

import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.google.common.net.HttpHeaders.LOCATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.BLANK;

class RedirectViewTest {
    @Test
    void renderTest() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET / HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));

        HttpResponse httpResponse = new HttpResponse(httpRequest);
        String redirectPath = "/test";

        View redirectView = new RedirectView(redirectPath);
        redirectView.render(Collections.emptyMap(), httpResponse);

        assertEquals(httpResponse.getResponseStatus(), ResponseStatus.FOUND);
        assertEquals(httpResponse.getHttpHeader().getHeaderAttribute(LOCATION), redirectPath);
    }

}