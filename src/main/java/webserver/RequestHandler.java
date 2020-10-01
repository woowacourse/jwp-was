package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Objects;
import model.ContentType;
import model.Method;
import model.Request;
import model.Status;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.StringUtils;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String REDIRECT_LOCATION = "/index.html";
    private static final CharSequence USER_CREATE_LOCATION = "/user/create";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    //정적 팩토리 메서드?
    public void run() {
        logger
            .debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream();
            OutputStream outputStream = connection.getOutputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String line = bufferedReader.readLine();
            Request request = Request.of(line);

            if (request.isSameMethod(Method.GET) || request.isSameMethod(Method.HEAD)) {
                makeResponse(dataOutputStream, request, Status.OK);
                return;
            }
            if (request.isSameMethod(Method.POST)) {
                if (request.getLocation().contains(USER_CREATE_LOCATION)) {
                    String parameters = StringUtils.getParameters(line, bufferedReader);
                    User user = User.of(parameters);

                    makeResponse(dataOutputStream, request, Status.FOUND);
                    return;
                }
                return;
            }
            makeResponse(dataOutputStream, request, Status.METHOD_NOT_ALLOWED);
        } catch (IOException | IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }

    private void makeResponse(DataOutputStream dataOutputStream, Request request, Status status) {
        responseHeader(dataOutputStream, request, status);
        responseBody(dataOutputStream, request);
    }

    private void responseHeader(DataOutputStream dataOutputStream, Request request, Status status) {
        ContentType contentType = request.getContentType();

        try {
            dataOutputStream.writeBytes(
                "HTTP/1.1 "
                    + status.getStatusCode()
                    + " "
                    + status.getStatusName()
                    + " \r\n");
            if (!Objects.isNull(contentType)) {
                dataOutputStream
                    .writeBytes("Content-Type: " + contentType.getContentType()
                        + ";charset=utf-8\r\n");
            }
            if (request.isNeedBody() && request.isSameMethod(Method.GET)) { // POST Body에 대한 정의 완료 후 리팩토링 예정
                byte[] body = FileIoUtils.loadFileFromClasspath(StringUtils.generatePath(request));
                dataOutputStream.writeBytes("Content-Length: " + body.length + "\r\n");
            }
            if (status.isNeedLocation()) {
                dataOutputStream.writeBytes("Location: " + REDIRECT_LOCATION + "\r\n");
            }
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dataOutputStream, Request request) {
        if(!request.isNeedBody() || request.isSameMethod(Method.POST)) { // POST Body에 대한 정의 완료 후 리팩토링 예정
            return;
        }
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(StringUtils.generatePath(request));
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
}
