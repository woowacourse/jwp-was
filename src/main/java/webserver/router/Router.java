package webserver.router;

import webserver.BadRequestException;
import webserver.controller.Controller;

public interface Router {
    default Controller retrieveController(String pattern) {
        throw new BadRequestException(pattern);
    }

    default boolean canHandle(String pattern) {
        return false;
    }
}
