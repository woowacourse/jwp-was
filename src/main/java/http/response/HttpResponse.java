package http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    //    private ResponseHeader responseHeader;
    private final IDataOutputStream dos;

    private HttpResponse(IDataOutputStream dos) {
        this.dos = dos;
    }

    public static HttpResponse of(IDataOutputStream dos) {
        return new HttpResponse(dos);
    }

    public void response200Header(int lengthOfBodyContent) {
        try {
            List<String> lines = Arrays.asList(
                    "HTTP/1.1 200 OK \r\n",
                    "Content-Type: text/html;charset=utf-8\r\n",
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
}
