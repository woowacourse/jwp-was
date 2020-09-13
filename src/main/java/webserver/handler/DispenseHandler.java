package webserver.handler;

import java.io.IOException;
import java.io.OutputStream;
import webserver.dto.HttpRequest;

public class DispenseHandler {

    private final FileHandler fileHandler;
    private final ApiHandler apiHandler;

    DispenseHandler() {
        this.fileHandler = new FileHandler();
        this.apiHandler = new ApiHandler();
    }

    void dispense(OutputStream out, HttpRequest httpRequest) throws IOException {
        if (httpRequest.isFile()) {
            fileHandler.loadFile(out, httpRequest);
            return;
        }
        apiHandler.handleAPI(out, httpRequest);
    }
}
