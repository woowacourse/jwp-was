package http.response;

import http.request.HttpRequest;
import http.response.core.Response;
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
    public void doResponse(DataOutputStream dos) throws IOException, URISyntaxException {
        ResponseStatus responseStatus = ResponseStatus.of("OK");
        ResponseContentType contentType = ResponseContentType.of(httpRequest.getRequestPath());
        responseHeader(dos, responseStatus, contentType.getContentType());
    }
}
