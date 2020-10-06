package webserver.controller;

import webserver.RequestException;

public class UndefinedExtension extends RequestException {
    public UndefinedExtension(String extension) {
        super(String.format("정의되지 않은 확장자입니다. {'extension' : %s}", extension));
    }
}
