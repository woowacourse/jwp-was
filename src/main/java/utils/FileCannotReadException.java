package utils;

import webserver.ServerException;

public class FileCannotReadException extends ServerException {
    public FileCannotReadException(String message) {
        super(message);
    }
}
