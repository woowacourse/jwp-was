package exceptions;

import webserver.http.request.RequestUri;

public class NotFoundURIException extends RuntimeException {
    public NotFoundURIException(RequestUri requestUri) {
        super("없는 경로 입니다: " + requestUri.getAbsPath());
    }

}
