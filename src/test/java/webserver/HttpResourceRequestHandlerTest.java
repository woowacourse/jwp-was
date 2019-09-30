package webserver;

import fileloader.TestFileLoader;
import http.common.HttpHeader;
import http.request.HttpRequest;
import http.request.RequestBody;
import http.request.RequestLine;
import http.response.HttpResponse;
import http.response.ResponseStatus;
import org.junit.jupiter.api.Test;
import webserver.httphandler.HttpResourceRequestHandler;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static utils.StringUtils.BLANK;

class HttpResourceRequestHandlerTest {
    private HttpResourceRequestHandler httpResourceRequestHandler =
            new HttpResourceRequestHandler(new TestFileLoader());

    @Test
    void 요청처리_성공() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET /index.html HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        httpResourceRequestHandler.handleHttpRequest(httpRequest, httpResponse);

        assertThat(httpResponse.getResponseStatus()).isEqualTo(ResponseStatus.OK);
    }

    @Test
    void 요청처리_실패() {
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine("GET " + TestFileLoader.WRONG_PATH + " HTTP/1.1"),
                new HttpHeader(),
                new RequestBody(BLANK, BLANK));
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        httpResourceRequestHandler.handleHttpRequest(httpRequest, httpResponse);

        assertThat(httpResponse.getResponseStatus()).isEqualTo(ResponseStatus.NOT_FOUND);
    }
}