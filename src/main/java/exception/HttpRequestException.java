package exception;

import webserver.response.ServletResponse;

public abstract class HttpRequestException extends HttpException {

    public HttpRequestException(final String message) {
        super(message);
    }

    public abstract ServletResponse getHandledResponse();
}
