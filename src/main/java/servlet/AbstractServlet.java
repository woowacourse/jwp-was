package servlet;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponseEntity;

public abstract class AbstractServlet implements Servlet {
    @Override
    public HttpResponseEntity handle(HttpRequest httpRequest) {
        if (HttpMethod.GET.match(httpRequest.getMethod())) {
            return doGet(httpRequest);
        }
        if (HttpMethod.POST.match(httpRequest.getMethod())) {
            return doPost(httpRequest);
        }
        return HttpResponseEntity.get405Response();
    }

    protected HttpResponseEntity doGet(HttpRequest httpRequest) {
        return HttpResponseEntity.get405Response();
    }

    protected HttpResponseEntity doPost(HttpRequest httpRequest) {
        return HttpResponseEntity.get405Response();
    }
}
