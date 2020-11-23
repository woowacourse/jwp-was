package jwp.was.webserver.handler;

import java.io.IOException;
import java.io.OutputStream;
import jwp.was.dto.HttpRequest;
import jwp.was.webapplicationserver.configure.controller.ControllerHandler;

public class DispenseHandler {

    private final FileHandler fileHandler;
    private final ControllerHandler controllerHandler;

    public DispenseHandler() {
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
