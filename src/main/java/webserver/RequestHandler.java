package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.FileIoUtils;
import webserver.protocol.RequestHeader;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final RequestHeader requestHeader = parseRequestHeader(in);

            final DataOutputStream dos = new DataOutputStream(out);
            final byte[] responseBody = FileIoUtils.loadFileFromClasspath(
                FileIoUtils.DEFAULT_PATH + requestHeader.getPath());
            response200Header(dos, responseBody.length);
            responseBody(dos, responseBody);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestHeader parseRequestHeader(final InputStream request) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(request));
        final String[] firstLineSplitResult = reader.readLine().split(" ", -1);
        return new RequestHeader(firstLineSplitResult[0], firstLineSplitResult[1],
            firstLineSplitResult[2]);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
