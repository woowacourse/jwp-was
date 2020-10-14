package controller;

class WrongRequestException extends RuntimeException {

    WrongRequestException(String message) {
        super(message);
    }
}
