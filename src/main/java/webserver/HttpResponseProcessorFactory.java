package webserver;

import webserver.httpRequest.HttpStatus;
import webserver.httpResponse.Http2xxResponseProcessor;
import webserver.httpResponse.Http3xxResponseProcessor;
import webserver.httpResponse.HttpResponse;

public class HttpResponseProcessorFactory {
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
