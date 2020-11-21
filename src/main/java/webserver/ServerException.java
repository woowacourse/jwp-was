package webserver;

import webserver.http.response.StatusCode;

public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public StatusCode getStatus() {
        return StatusCode.INTERNAL_SERVER_ERROR;
    }
}
