package webserver;

import org.junit.jupiter.api.Test;
import webserver.httpResponse.Http2xxResponseProcessor;
import webserver.httpResponse.HttpResponse;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseProcessorFactoryTest {

    @Test
    void 이백_OK_프로세서_생성_테스트() {
        HttpResponseProcessorFactory httpResponseProcessorFactory = new HttpResponseProcessorFactory();
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.forward();

        HttpResponseProcessor httpResponseProcessor = httpResponseProcessorFactory.getHttpResponseProcessor(httpResponse);
        assertThat(httpResponseProcessor).isInstanceOf(Http2xxResponseProcessor.class);
    }

    @Test
    void 삼백_REDIRECT_프로세서_생성_테스트() {
        HttpResponseProcessorFactory httpResponseProcessorFactory = new HttpResponseProcessorFactory();
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.sendRedirect();

        HttpResponseProcessor httpResponseProcessor = httpResponseProcessorFactory.getHttpResponseProcessor(httpResponse);
        assertThat(httpResponseProcessor).isInstanceOf(Http2xxResponseProcessor.class);
    }
}