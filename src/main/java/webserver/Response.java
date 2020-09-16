package webserver;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class Response {
    private HttpStatus status;
    private FileResponse fileResponse;
    private String location;

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
        dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
        dos.writeBytes("Location: " + this.location + "\r\n");
        dos.flush();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes(String.format("Content-Type: %s;charset=utf-8\r\n", fileResponse.getContentType()));
        dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

}
