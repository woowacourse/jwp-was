package webserver.handler;

import java.io.IOException;
import java.io.OutputStream;
import webserver.dto.HttpRequest;

public class DispenseHandler {

    static void dispense(OutputStream out, HttpRequest httpRequest) throws IOException {
        if (httpRequest.isFile()) {
            FileHandler.loadFile(out, httpRequest);
            return;
        }
        ApiHandler.handleAPI();
    }
}
