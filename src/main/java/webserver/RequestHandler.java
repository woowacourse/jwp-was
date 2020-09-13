package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import utils.FileIoUtils;
import utils.IOUtils;

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
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            StringBuilder headerLines = new StringBuilder();
            while (Objects.nonNull(line) && !line.isEmpty()) {
                headerLines.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            RequestHeader requestHeader = RequestHeader.of(headerLines.toString());
            String contentLength = requestHeader.getHeader("Content-Length");
            if (Objects.nonNull(contentLength)) {
                String bodyLine = IOUtils.readData(bufferedReader, Integer.parseInt(
                    contentLength));
                RequestBody requestBody = RequestBody.of(bodyLine);
                User user = convert(User.class, requestBody.getAttribute());
            }

            byte[] body = FileIoUtils.loadFileFromClasspath(requestHeader.getPath());
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private <T> T convert(Class<T> clazz, Map<String, String> queryParams) {
        try {
            T instance = clazz.getConstructor().newInstance();
            queryParams.forEach((key, value) -> {
                try {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(instance, value);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return instance;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new MethodParameterBindException();
        } catch (NoSuchMethodException e) {
            throw new NoDefaultConstructorException(clazz);
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
