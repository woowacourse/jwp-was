package utils;

import webserver.SystemException;

public class FileCannotReadException extends SystemException {
    public FileCannotReadException(String message) {
        super(message);
    }
}
