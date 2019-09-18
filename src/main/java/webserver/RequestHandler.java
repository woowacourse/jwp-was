package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Arrays;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = new byte[]{};

            String path = line.split(" ")[1];
            if(path.contains(".html")) {
                body = FileIoUtils.loadFileFromClasspath(String.format("./templates%s", path));
            } else {
                String decodedUri = URLDecoder.decode(path, "UTF-8");
                logger.debug(decodedUri);

                String[] params = decodedUri.split("\\?")[1].split("&");
                logger.debug(Arrays.toString(params));

                Map<String,String> kv = new HashMap<>();
                for (String param : params) {
                    String[] tmp = param.split("=");
                    kv.put(tmp[0], tmp[1]);
                }
                logger.debug("kv: " + kv);

                User user = new User(
                        kv.get("userId"),
                        kv.get("password"),
                        kv.get("name"),
                        kv.get("email"));

                logger.debug("user: " + user);
            }

            while (!"".equals(line)) {
                logger.debug(line);
                if (line == null) {
                    return;
                }
                line = br.readLine();
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
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
