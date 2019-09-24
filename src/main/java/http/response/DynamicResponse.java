package http.response;

import http.request.HttpRequest;
import http.response.core.Response;
import http.response.core.ResponseBody;
import http.response.core.ResponseContentType;
import http.response.core.ResponseStatus;

public class DynamicResponse extends Response {
    private static final String REDIRECT_URL = "Location: http://localhost:8080/\r\n";

    DynamicResponse(HttpRequest httpRequest) {
        super(httpRequest);
    }

    @Override
    public ResponseBody doResponse() {
        if (httpRequest.getData().isEmpty()) {
            ResponseStatus responseStatus = ResponseStatus.of(200);
            ResponseContentType contentType = ResponseContentType.of(httpRequest.getRequestPath());
            return new ResponseBody(httpRequest, responseStatus, contentType.getContentType());
        }
        ResponseStatus responseStatus = ResponseStatus.of(302);
        return new ResponseBody(httpRequest, responseStatus, REDIRECT_URL);
    }


}
