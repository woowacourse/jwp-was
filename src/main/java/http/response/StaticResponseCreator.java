package http.response;

import http.request.HttpRequest;
import http.response.core.Response;
import http.response.core.ResponseCreator;

public class StaticResponseCreator implements ResponseCreator {
    @Override
    public Response create(HttpRequest httpRequest) {
        return new StaticResponse(httpRequest);
    }
}
