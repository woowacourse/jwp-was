package webserver.controller;

import webserver.ClientException;
import webserver.http.response.StatusCode;

public class UndefinedExtensionException extends ClientException {
    public UndefinedExtensionException(String extension) {
        super(String.format("정의되지 않은 확장자입니다. {'extension' : %s}", extension));
    }

    @Override
    public StatusCode getStatus() {
        return StatusCode.BAD_REQUEST;
    }
}
