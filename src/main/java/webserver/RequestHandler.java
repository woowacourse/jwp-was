package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
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
            RequestHeader requestHeader = createRequestHeader(br);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = new byte[]{};

            logger.debug("request header: {}", requestHeader);
            if (requestHeader.getRequestMethodType() == RequestMethodType.GET) {
                String path = requestHeader.getRequestUri().split("\\?")[0];
                // TODO: 2019-09-18 정적 html이 올때에는 template을 찾는게 아닌 static을 찾아야함.
                if (path.contains(".css")) {
                    body = FileIoUtils.loadFileFromClasspath(String.format("./static%s", path));
                    response200Header(dos, body.length, "text/css");
                    responseBody(dos, body);
                }
                if (path.contains(".js")) {
                    body = FileIoUtils.loadFileFromClasspath(String.format("./static%s", path));
                    response200Header(dos, body.length, "application/javascript");
                    responseBody(dos, body);
                }
                if (path.contains(".html")) {
                    body = FileIoUtils.loadFileFromClasspath(String.format("./templates%s", path));
                    response200Header(dos, body.length,"text/html");
                    responseBody(dos, body);
                }

                if (path.equals("/create")) {
                    String requestParams = requestHeader.getRequestUri().split("\\?")[1];
                    Map<String, String> kv = convertRequestDataToMap(requestParams);
                    logger.debug("kv: " + kv);

                    User user = new User(
                            kv.get("userId"),
                            kv.get("password"),
                            kv.get("name"),
                            kv.get("email"));

                    DataBase.addUser(user);
                    logger.debug("user: " + user);
                    response200Header(dos, body.length,"text/html");
                    responseBody(dos, body);
                }
            }

            if (requestHeader.getRequestMethodType() == RequestMethodType.POST) {
                String path = requestHeader.getRequestUri();
                if (path.equals("/user/create")) {
                    String requestParams = requestHeader.getRequestBody();
                    Map<String, String> kv = convertRequestDataToMap(requestParams);
                    logger.debug("kv: " + kv);

                    User user = new User(
                            kv.get("userId"),
                            kv.get("password"),
                            kv.get("name"),
                            kv.get("email"));

                    DataBase.addUser(user);
                    logger.debug("user: " + user);

                    response302Header(dos, "/index.html");
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    private Map<String, String> convertRequestDataToMap(String requestParams) throws UnsupportedEncodingException {
        logger.debug(requestParams);
        String decodedParams = URLDecoder.decode(requestParams, "UTF-8");
        logger.debug(decodedParams);
        Map<String, String> kv = new HashMap<>();
        for (String param : decodedParams.split("&")) {
            String[] tmp = param.split("=");
            kv.put(tmp[0], tmp[1]);
        }
        return kv;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "; charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.format("Location: %s \r\n", location));
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

    private RequestHeader createRequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();
        logger.debug(line);

        String methodType = line.split(" ")[0];
        String path = line.split(" ")[1];
        if ("GET".equals(methodType)) {
            return new RequestHeader(RequestMethodType.GET, path);
        }

        if ("POST".equals(methodType)) {
            int contentLength = 0;
            while (!"".equals(line)) {
                logger.debug(line);
                if (line == null) {
                    break;
                }
                if (line.contains("Content-Length:")) {
                    contentLength = Integer.parseInt(line.split(" ")[1]);
                }
                line = br.readLine();
            }
            return new RequestHeader(RequestMethodType.POST, path, IOUtils.readData(br, contentLength));
        }

        throw new NotSupportedMethodTypeException();
    }
}
