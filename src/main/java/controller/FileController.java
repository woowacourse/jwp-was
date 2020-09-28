package controller;

import webserver.request.HttpRequest;
import webserver.response.FileResponse;
import webserver.response.HttpResponse;
import webserver.response.Status;

public class FileController {

    public static HttpResponse getPage(HttpRequest httpRequest) {
        FileResponse fileResponse = new FileResponse("./templates" + httpRequest.getPath(), "text/html");
        return HttpResponse.withFileResponse(Status.OK, fileResponse);
    }

    public static HttpResponse getCss(HttpRequest httpRequest) {
        FileResponse fileResponse = new FileResponse("./static/" + httpRequest.getPath(), "text/css");
        return HttpResponse.withFileResponse(Status.OK, fileResponse);
    }
}
