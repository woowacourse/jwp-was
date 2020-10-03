package webserver.http.request;

import webserver.RequestException;

public class NotReadableHttpMessageException extends RequestException {
    public NotReadableHttpMessageException(String message) {
        super(message);
    }
}
