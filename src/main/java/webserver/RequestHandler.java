package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            byte[] body = "Hello World".getBytes();

            while (!"".equals(line)) {
                if (line == null) {
                    break;
                }

                if (line.startsWith("GET")) {
                    String fullResponse = line.split(" ")[1].substring(1);
                    logger.debug("fullResponse : {}", fullResponse);
                    String[] split = fullResponse.split("\\?");
                    String file = split[0];

                    Map<String, String> userParams = new HashMap<>();
                    if (split.length == 2) {
                        String variable = split[1];
                        logger.debug("file = {}", file);
                        logger.debug("variable = {}", variable);

                        String[] variables = variable.split("&");
                        for (String each : variables) {
                            logger.debug("each = {}", each);
                            String[] split1 = each.split("=");
                            String key = split1[0];
                            String value = "";
                            if (split1.length == 2) {
                                value = split1[1];
                            }

                            userParams.put(key, value);

                            logger.debug("{key, debug} {} {}", key, value);
                        }
                        User user = new User(userParams.get("userId"), userParams.get("password"), userParams.get("name"), userParams.get("email"));
                        DataBase.addUser(user);
                    }
                    body = FileIoUtils.loadFileFromClasspath("./templates/" + file);
                }
                line = br.readLine();
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
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
