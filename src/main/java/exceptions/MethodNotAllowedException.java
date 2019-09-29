package exceptions;

import webserver.http.request.RequestMethod;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException(RequestMethod requestMethod) {
        super("지원하지 않는 메소드 입니다: " + requestMethod.method);
    }
}
