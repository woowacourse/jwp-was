package webserver.handler;

import static webserver.Kind.API;
import static webserver.Kind.STATIC_FILE;
import static webserver.Kind.WEBAPP_FILE;

import java.io.IOException;
import java.io.OutputStream;
import webserver.dto.HttpRequest;

public class DispenseHandler {

    static void dispense(OutputStream out, HttpRequest httpRequest) throws IOException {
        if (httpRequest.isSameKind(WEBAPP_FILE)) {
            HtmlFileHandler.loadHtmlFile(out, httpRequest);
        }
        if (httpRequest.isSameKind(API)) {
            ApiHandler.handleAPI();
        }
        if (httpRequest.getUrlPath().endsWith(".css") || httpRequest.isSameKind(STATIC_FILE)) {
            StaticFileHandler.loadStaticFile(out, httpRequest);
        }
    }
}
