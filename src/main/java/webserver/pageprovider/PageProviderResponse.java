package webserver.pageprovider;

import http.response.HttpResponse;
import http.response.HttpResponseAccessor;

public class PageProviderResponse implements HttpResponseAccessor {
    private final HttpResponse response;

    private PageProviderResponse(HttpResponse response) {
        this.response = response;
    }

    public static HttpResponseAccessor from(HttpResponse response) {
        return new PageProviderResponse(response);
    }


}
