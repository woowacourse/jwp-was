package webserver.router;

import webserver.BadRequestException;
import webserver.controller.Controller;

public class FileServerRouter implements Router {

    @Override
    public Controller retrieveController(String pattern) {
        throw new BadRequestException(pattern);
    }

    @Override
    public boolean canHandle(String pattern) {
        // static 확인
        // templates 확인

        return false;
    }
}
