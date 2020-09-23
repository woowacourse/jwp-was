package webserver.response;

import static webserver.Status.CREATED;
import static webserver.Status.FOUND;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import utils.FileIoUtils;
import webserver.AcceptType;
import webserver.Body;
import webserver.Headers;
import webserver.Status;
import webserver.request.Request;

public class Response {

    private String version;
    private Status status;
    private Headers headers;
    private Body body;

    public Response() {
        this.headers = new Headers();
    }

    public static Response emptyResponse() {
        return new Response();
    }

    public void getResponse(Request request) {
        AcceptType type = request.getType();
        this.body = new Body(fileDataFinder(request));

        if (body.isEmpty()) {
            this.status = Status.NOT_FOUND;
        } else {
            this.status = Status.OK;
        }

        this.version = request.getVersion();
        headers.setHeader("Content-Type", type.getContentType() + ";charset=utf-8");
        headers.setHeader("Content-Length", String.valueOf(body.getLength()));
    }

    public void postResponse(Request request) {
        this.status = CREATED;
        this.version = request.getVersion();
    }

    public void redirectTo(Request request, String url) {
        this.status = FOUND;
        this.version = request.getVersion();
        headers.setHeader("Location", url);
    }

    private byte[] fileDataFinder(Request request) {
        AcceptType type = request.getType();
        try {
            return FileIoUtils.loadFileFromClasspath(type.getFileRootPath() + request.getPath());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getVersion() {
        return version;
    }

    public Status getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers.getHeaders();
    }

    public boolean isBodyEmpty() {
        return body == null;
    }

    public byte[] getBody() {
        return body.getBody();
    }
}
