package webserver;

import webserver.http.response.StatusCode;

public abstract class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }

    public abstract StatusCode getStatus();
}
