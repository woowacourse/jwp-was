package http.response;

import http.request.HttpRequest;
import http.response.core.Response;
import http.response.core.ResponseContentType;
import http.response.core.ResponseStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class DynamicResponse extends Response {
    private static final String REDIRECT_URL = "Location: http://localhost:8080/index.html \r\n";

    DynamicResponse(HttpRequest httpRequest) {
        super(httpRequest);
    }

    @Override
    public void doResponse(DataOutputStream dos) throws IOException, URISyntaxException {
        if (httpRequest.getData().isEmpty()) {
            ResponseStatus responseStatus = ResponseStatus.of("OK");
            ResponseContentType contentType = ResponseContentType.of(httpRequest.getRequestPath());
            responseHeader(dos, responseStatus, contentType.getContentType());
        } else {
            ResponseStatus responseStatus = ResponseStatus.of("Found");
            responseHeader(dos, responseStatus, REDIRECT_URL);
        }

    }


}
