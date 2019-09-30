package webserver.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {
    private DataOutputStream dos;
    private HttpStatus httpStatus;
    private ResponseHeader header;
    private byte[] body;

    public HttpResponse(DataOutputStream docs) {
        this.dos = docs;
        this.header = new ResponseHeader();
        this.body = new byte[0];
    }

    public void ok(byte[] body) throws IOException {
        this.httpStatus = HttpStatus.OK;
        this.body = body;
        render();
    }

    public void found() throws IOException {
        this.httpStatus = HttpStatus.FOUND;
        render();
    }

    public void error(HttpStatus httpStatus, String message) throws IOException {
        this.httpStatus = httpStatus;
        this.body = message.getBytes();
        setContentLengthAndType(body.length, "text/html");
        setContentEncoding("utf-8");
        render();
    }

    private void render() throws IOException {
        writeLine();
        writeHeader();
        writeBody();
        end();
    }

    private void writeLine() throws IOException {
        dos.writeBytes("HTTP/1.1 " + httpStatus.getCode() + " " + httpStatus.getName() + "\r\n");
    }

    private void writeHeader() throws IOException {
        for (Map.Entry value : header.entrySet()) {
            dos.writeBytes(value.getKey() + ": " + value.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void writeBody() throws IOException {
        dos.write(body, 0, body.length);
    }

    private void end() throws IOException {
        dos.flush();
    }


    public void setContentLengthAndType(int length, String type) {
        header.setContentLengthAndType(length, type);
    }

    public void setContentEncoding(String encoder) {
        String type = (String) header.get("Content-Type");
        type = type + ";charset=" + encoder;
        header.put("Content-Type", type);
    }

    public void setCookie(String key, String value) {
        header.setCookie(key, value);
    }

    public void removeCookie(String key) {
        header.removeCookie(key);
    }

    public void setLocation(String path) {
        header.setLocation(path);
    }
}


