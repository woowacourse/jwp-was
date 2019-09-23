package webserver.response;

import webserver.request.HttpRequest;

public class FoundResponseMetaData extends AbstractResponseMetaData {
    private static final HttpStatus httpStatus = HttpStatus.FOUND;

    public FoundResponseMetaData(final HttpRequest httpRequest, String location) {
        super(httpRequest);
        responseHeaderFields.put("Location", location);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
