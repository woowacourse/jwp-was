package webserver;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class Response {
    private final HttpStatus status;
    private final FileResponse fileResponse;
    private final String location;

    private Response(HttpStatus status, FileResponse fileResponse, String location) {
        this.status = status;
        this.fileResponse = fileResponse;
        this.location = location;
    }

    public static Response withFileResponse(HttpStatus status, FileResponse fileResponse) {
        return new Response(status, fileResponse, null);
    }

    public static Response withLocation(HttpStatus status, String location) {
        return new Response(status, null, location);
    }

    public void respond(DataOutputStream dos) throws IOException, URISyntaxException {
        if (status == HttpStatus.FOUND) {
            redirect302Header(dos);
            return;
        }
        byte[] body = FileIoUtils.loadFileFromClasspath(fileResponse.getFilePath());
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void redirect302Header(DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("%s %s \r\n", HttpMessage.HTTP_VERSION.getName(), HttpMessage.STATUS_FOUND.getName()));
        dos.writeBytes(String.format("%s: %d \r\n", HttpMessage.LOCATION.getName(), this.location));
        dos.flush();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) throws IOException {
        dos.writeBytes(String.format("%s %s \r\n", HttpMessage.HTTP_VERSION.getName(), HttpMessage.STATUS_OK.getName()));
        dos.writeBytes(String.format("%s: %s;charset=utf-8 \r\n", HttpMessage.CONTENT_TYPE, fileResponse.getContentType()));
        dos.writeBytes(String.format("%s: %d \r\n", HttpMessage.CONTENT_LENGTH, lengthOfBodyContent));
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

}
