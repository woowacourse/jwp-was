package http.response;

import http.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private StatusCode code;
    //    private ResponseHeader responseHeader;
    private final IDataOutputStream dos;

    private HttpResponse(IDataOutputStream dos) {
        this.dos = dos;
    }

    public static HttpResponse of(IDataOutputStream dos) {
        return new HttpResponse(dos);
    }

    public static HttpResponse ok(IDataOutputStream dos) {
       // code = StatusCode.Ok;
        return new HttpResponse(dos);
    }

   //  public void response200Header(int lengthOfBodyContent, String contentType) {

    public void response200Header(int lengthOfBodyContent, String contentType) {
        try {
            List<String> lines = Arrays.asList(
                    String.format("HTTP/1.1 %d %s \r\n", 200, "OK"),

                    "Content-Type: " + contentType + ";charset=utf-8\r\n",
                    "Content-Length: " + lengthOfBodyContent + "\r\n",
                    "\r\n");

            for (String line : lines) {
                dos.writeBytes(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void response302Header(String location) {
        try{
            String header = "HTTP/1.1 302 Found\n" +
                    "Location: " + location;
            dos.writeBytes(header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
