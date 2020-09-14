package webserver.handler;

import controller.handler.ControllerHandler;
import java.io.IOException;
import java.io.OutputStream;
import webserver.dto.HttpRequest;

public class DispenseHandler {

    private final FileHandler fileHandler;
    private final ControllerHandler controllerHandler;

    public DispenseHandler() throws NoSuchFieldException, IllegalAccessException {
        this.fileHandler = new FileHandler();
        this.controllerHandler = new ControllerHandler();
    }

    void dispense(OutputStream out, HttpRequest httpRequest) throws IOException {
        if (httpRequest.isFile()) {
            fileHandler.loadFile(out, httpRequest);
            return;
        }
        controllerHandler.handleAPI(out, httpRequest);
    }
}
