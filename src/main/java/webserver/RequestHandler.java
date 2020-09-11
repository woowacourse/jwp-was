package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import web.RequestBody;
import web.RequestHeader;
import web.RequestLine;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String RESOURCE_BASE_PATH = "./templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(out);

            String firstLine = br.readLine();
            final RequestLine requestLine = new RequestLine(firstLine);

            List<String> requestHeaders = new ArrayList<>();
            String request;
            while (!(request = br.readLine()).equals("")) {
                requestHeaders.add(request);
            }
            final RequestHeader header = new RequestHeader(requestHeaders);

            final int contentLength = header.getContentLength();
            final String body = IOUtils.readData(br, contentLength);
            final RequestBody requestBody = new RequestBody(body);

            String url = requestLine.requestUrl();

            if (requestLine.getMethod().equals("POST") && url.equals("/user/create")) {
                createUser(requestBody);
                response302Header(dos, "/index.html");
            }

            if (url.contains(".html")) {
                byte[] staticFile = FileIoUtils.loadFileFromClasspath(RESOURCE_BASE_PATH + url);
                response200Header(dos, staticFile.length);
                responseBody(dos, staticFile);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void createUser(RequestBody body) {
        final Map<String, String> parameters = body.parseParameters();
        final User user = new User(parameters.get("userId"), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        DataBase.addUser(user);
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + " \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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
