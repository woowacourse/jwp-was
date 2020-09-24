package controller;

import webserver.request.Request;
import webserver.response.FileResponse;
import webserver.response.Response;
import webserver.response.Status;

public class FileController {

    public static Response getPage(Request request) {
        FileResponse fileResponse = new FileResponse("./templates" + request.getResource(), "text/html");
        return Response.withFileResponse(Status.OK, fileResponse);
    }

    public static Response getCss(Request request) {
        FileResponse fileResponse = new FileResponse("./static/" + request.getResource(), "text/css");
        return Response.withFileResponse(Status.OK, fileResponse);
    }
}
