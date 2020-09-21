package controller;

import webserver.FileResponse;
import webserver.HttpStatus;
import webserver.Request;
import webserver.Response;

public class FileController {

    public static Response getPage(Request request) {
        FileResponse fileResponse = new FileResponse("./templates" + request.getResource(), "text/html");
        return Response.withFileResponse(HttpStatus.OK, fileResponse);
    }

    public static Response getCss(Request request) {
        FileResponse fileResponse = new FileResponse("./static/" + request.getResource(), "text/css");
        return Response.withFileResponse(HttpStatus.OK, fileResponse);
    }
}
