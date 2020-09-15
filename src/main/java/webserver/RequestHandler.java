package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import web.HttpMethod;
import web.RequestBody;
import web.RequestHeader;
import web.RequestLine;
import web.StaticFile;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            RequestLine requestLine = RequestLine.of(br.readLine());
            RequestHeader requestHeader = new RequestHeader(br);
            RequestBody requestBody = null;
            if (HttpMethod.POST == requestLine.getMethod()) {
                requestBody = RequestBody.of(br, requestHeader.getValue("Content-Length"));
            }
            String path = requestLine.getPath();
            byte[] body;
            if (path.startsWith("/user/create")) {
                Map<String, String> params = requestBody.getParams();
                User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
                DataBase.addUser(user);
                body = user.toString().getBytes();
                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
                responseBody(dos, body);
            } else if (HttpMethod.GET == requestLine.getMethod()) {
                DataOutputStream dos = new DataOutputStream(out);
                StaticFile staticFile = StaticFile.of(path);
                body = FileIoUtils.loadFileFromClasspath(staticFile.getPrefix() + path);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
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
