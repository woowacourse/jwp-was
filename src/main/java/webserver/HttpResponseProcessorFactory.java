package webserver;

import webserver.httpRequest.HttpStatus;
import webserver.httpResponseProcessor.Http2xxResponseProcessor;
import webserver.httpResponseProcessor.Http3xxResponseProcessor;

public class HttpResponseProcessorFactory {

    private HttpResponseProcessorFactory() {
    }

    public static HttpResponseProcessorFactory getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final HttpResponseProcessorFactory INSTANCE = new HttpResponseProcessorFactory();
    }

    public HttpResponseProcessor getHttpResponseProcessor(HttpResponse httpResponse) {
        if (httpResponse.getStatus() == HttpStatus.OK) {
            return new Http2xxResponseProcessor();
        }

        if (httpResponse.getStatus() == HttpStatus.FOUND) {
            return new Http3xxResponseProcessor();
        }

        throw new IllegalArgumentException("지원 하지 않는 Http 상태 입니다.");
    }
}
