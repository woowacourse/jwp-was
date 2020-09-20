package webserver.http.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.controller.PageController;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeaders;
import webserver.http.request.RequestMethod;
import webserver.http.request.RequestLine;
import webserver.http.request.RequestUrl;
import webserver.http.response.HttpResponse;

class PageControllerTest {
    private PageController pageController = new PageController();

    @DisplayName("페이지를 표시한다.")
    @Test
    void viewPage() throws Exception {
        RequestLine requestLine = new RequestLine(RequestMethod.GET, RequestUrl.from("/index.html"),
            "HTTP/1.1");
        RequestHeaders requestHeaders = new RequestHeaders(new HashMap<>());
        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, new RequestBody(""));
        HttpResponse httpResponse = HttpResponse.ofVersion(httpRequest.getHttpVersion());

        pageController.service(httpRequest, httpResponse);

        assertThat(httpResponse)
            .extracting("responseStatus")
            .extracting("httpStatus")
            .extracting("statusCode").isEqualTo(200);
    }

    @DisplayName("없는 파일 호출 시 404 호출")
    @Test
    void viewPageWithNotFound() throws Exception {
        RequestLine requestLine = new RequestLine(RequestMethod.GET, RequestUrl.from("/abc"),
            "HTTP/1.1");
        RequestHeaders requestHeaders = new RequestHeaders(new HashMap<>());
        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, new RequestBody(""));
        HttpResponse httpResponse = HttpResponse.ofVersion(httpRequest.getHttpVersion());

        pageController.service(httpRequest, httpResponse);

        assertThat(httpResponse)
            .extracting("responseStatus")
            .extracting("httpStatus")
            .extracting("statusCode").isEqualTo(404);
    }

    @DisplayName("해당되지 않는 메소드 호출 시 405 호출")
    @Test
    void viewPageWithNotAllowed() throws Exception {
        RequestLine requestLine = new RequestLine(RequestMethod.POST, RequestUrl.from("/index.html"),
            "HTTP/1.1");
        RequestHeaders requestHeaders = new RequestHeaders(new HashMap<>());
        HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, new RequestBody(""));
        HttpResponse httpResponse = HttpResponse.ofVersion(httpRequest.getHttpVersion());

        pageController.service(httpRequest, httpResponse);

        assertThat(httpResponse)
            .extracting("responseStatus")
            .extracting("httpStatus")
            .extracting("statusCode").isEqualTo(405);
    }
}