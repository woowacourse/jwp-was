package webserver.http.request;

import webserver.ClientException;
import webserver.http.response.StatusCode;

public class NotReadableHttpMessageException extends ClientException {
    public NotReadableHttpMessageException(String message) {
        super(message);
    }

    @Override
    public StatusCode getStatus() {
        return StatusCode.BAD_REQUEST;
    }
}
