package http.response;

import http.request.HttpRequest;
import http.response.core.Response;
import http.response.core.ResponseBody;
import http.response.core.ResponseContentType;
import http.response.core.ResponseStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResponse extends Response {
    StaticResponse(HttpRequest httpRequest) {
        super(httpRequest);
    }

    @Override
    public ResponseBody doResponse() {
        ResponseStatus responseStatus = ResponseStatus.of(200);
        ResponseContentType contentType = ResponseContentType.of(httpRequest.getRequestPath());
        return new ResponseBody(httpRequest, responseStatus, contentType.getContentType());
    }
}
