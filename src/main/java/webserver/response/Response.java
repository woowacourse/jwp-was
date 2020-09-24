package webserver.response;

import utils.FileIoUtils;
import webserver.EntityHeader;
import webserver.HttpVersion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class Response {
    private final Status status;
    private final FileResponse fileResponse;
    private final String location;

    private Response(Status status, FileResponse fileResponse, String location) {
        this.status = status;
        this.fileResponse = fileResponse;
        this.location = location;
    }

    public static Response withFileResponse(Status status, FileResponse fileResponse) {
        return new Response(status, fileResponse, null);
    }

    public static Response withLocation(Status status, String location) {
        return new Response(status, null, location);
    }

    public void respond(DataOutputStream dos) throws IOException, URISyntaxException {
        if (status == Status.FOUND) {
            redirect302Header(dos);
            return;
        }
        byte[] body = FileIoUtils.loadFileFromClasspath(fileResponse.getFilePath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void redirect302Header(DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("%s %s \r\n", HttpVersion.USING_VERSION.get(), Status.FOUND.getStatus()));
        dos.writeBytes(ResponseHeader.LOCATION.make(this.location) + " \r\n");
        dos.flush();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) throws IOException {
        dos.writeBytes(String.format("%s %s \r\n", HttpVersion.USING_VERSION.get(), Status.OK.getStatus()));
        dos.writeBytes(EntityHeader.CONTENT_TYPE.make(fileResponse.getContentType()) + " \r\n");
        dos.writeBytes(EntityHeader.CONTENT_LENGTH.make(String.valueOf(lengthOfBodyContent)) + " \r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

}
