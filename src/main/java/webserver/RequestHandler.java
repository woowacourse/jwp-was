package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.PathUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            router(br, dos);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    private void router(BufferedReader br, DataOutputStream dos) throws IOException, URISyntaxException {
        String line = br.readLine();
        String method = PathUtils.parseMethod(line);
        String path = PathUtils.parsePath(line);
        if (method.equals("POST") && path.equals("/user/create")) {
            while (!"".equals(br.readLine())) {
            }
            line = br.readLine();
            String[] temp = line.split("&");
            Map<String, String> params = new HashMap<>();
            for (String param : temp) {
                String[] keyValues = param.split("=");
                params.put(keyValues[0], keyValues[1]);
            }
            User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
            DataBase.addUser(user);
        } else {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + path);
            response200Header(dos, body.length);
            responseBody(dos, body);
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
