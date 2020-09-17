package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.body.DefaultHttpBody;
import webserver.http.body.HttpBody;
import webserver.http.header.HttpHeader;
import webserver.http.message.HttpMessage;
import webserver.http.message.HttpRequestMessage;
import webserver.http.request.HttpUri;
import webserver.http.response.HttpStatus;
import webserver.http.response.StatusLine;
import webserver.service.UserService;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                     connection.getPort());

        try (InputStream in = connection.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {

            HttpRequestMessage httpRequestMessage = HttpRequestMessage.from(br);
            HttpBody httpBody = httpRequestMessage.getHttpBody();

            UserService.addUser(httpBody);

            HttpUri httpUri = httpRequestMessage.getRequestLine().getHttpUri();
            HttpMessage httpResponseMessage = createHttpMessage(httpUri);

            response(dos, httpResponseMessage.toHttpMessage().getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpMessage createHttpMessage(HttpUri httpUri) {
        try {
            byte[] fileBytes = httpUri.readFile();
            StatusLine statusLine = new StatusLine(HttpStatus.OK);
            HttpHeader httpHeader = new HttpHeader.Builder()
                    .addHeader("Content-Type", httpUri.getContentType() + ";charset=utf-8")
                    .addHeader("Content-Length", String.valueOf(fileBytes.length))
                    .build();
            HttpBody httpbody = new DefaultHttpBody(new String(fileBytes, 0, fileBytes.length));
            return new HttpMessage(statusLine, httpHeader, httpbody);
        } catch (Exception e) {
            StatusLine statusLine = new StatusLine(HttpStatus.FOUND);
            String redirectUrl = "/index.html";
            HttpHeader httpHeader = new HttpHeader.Builder()
                    .addHeader("Location", redirectUrl)
                    .build();
            return new HttpMessage(statusLine, httpHeader);
        }
    }

    private void response(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
