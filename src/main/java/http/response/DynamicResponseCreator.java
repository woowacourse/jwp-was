package http.response;

import http.request.HttpRequest;
import http.response.core.Response;
import http.response.core.ResponseCreator;

public class DynamicResponseCreator implements ResponseCreator {

    @Override
    public Response create(HttpRequest httpRequest) {
        return new DynamicResponse(httpRequest);
    }
}
