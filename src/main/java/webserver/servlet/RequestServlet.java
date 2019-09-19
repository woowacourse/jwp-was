package webserver.servlet;

import org.apache.commons.httpclient.HttpMethod;
import webserver.request.HttpRequest;
import webserver.request.RequestMethod;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class RequestServlet implements HttpServlet {
    @Override
    public HttpResponse run(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            return doGet(httpRequest);
        }

        if (httpRequest.getMethod() == RequestMethod.POST) {
            return doPost(httpRequest);
        }
        throw new IllegalArgumentException("지원하지 않는 메소드 입니다.");
    }

    public HttpResponse doGet(HttpRequest httpRequest) throws IOException, URISyntaxException {
        throw new IllegalArgumentException("지원하지 않는 메소드 입니다.");
    };

    public HttpResponse doPost(HttpRequest httpRequest){
        throw new IllegalArgumentException("지원하지 않는 메소드 입니다.");
    };
}
