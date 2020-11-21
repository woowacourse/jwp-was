package exception;

import webserver.response.ServletResponse;
import webserver.response.StatusCode;

public abstract class HttpRequestException extends HttpException {

    public HttpRequestException(final String message) {
        super(message);
    }

    public abstract StatusCode getStatusCode();
}
