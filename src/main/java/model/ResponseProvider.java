package model;

import db.DataBase;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.StringUtils;
import webserver.RequestHandler;

public enum ResponseProvider {

    GET_RESPONSE(Method.GET, (dataOutputStream, request) -> {
        try {
            makeResponse(dataOutputStream, request, Status.OK);
        } catch (IOException | URISyntaxException e) {
            Logger logger = LoggerFactory.getLogger(RequestHandler.class);
            logger.error(e.getMessage());
        }
    }),
    POST_RESPONSE(Method.POST, (dataOutputStream, request) -> {
        if (request.getLocation().contains("/user/create")) {
            String parameters = request.getParameters();
            User user = User.of(parameters);
            DataBase.addUser(user);

            try {
                makeResponse(dataOutputStream, request, Status.FOUND);
            } catch (IOException | URISyntaxException e) {
                Logger logger = LoggerFactory.getLogger(RequestHandler.class);
                logger.error(e.getMessage());
            }
        }
    }),
    UNKNOWN_RESPONSE(null, (dataOutputStream, request) -> {
        try {
            makeResponse(dataOutputStream, request, Status.METHOD_NOT_ALLOWED);
        } catch (IOException | URISyntaxException e) {
            Logger logger = LoggerFactory.getLogger(RequestHandler.class);
            logger.error(e.getMessage());
        }
    });

    private final Method methods;
    private final BiConsumer<DataOutputStream, Request> operation;

    ResponseProvider(Method methods, BiConsumer<DataOutputStream, Request> operation) {
        this.methods = methods;
        this.operation = operation;
    }

    public static ResponseProvider of(Request request) {
        return Arrays.stream(values())
            .filter(r -> request.isSameMethod(r.methods))
            .findFirst()
            .orElse(UNKNOWN_RESPONSE);
    }

    public void executeOperation(DataOutputStream dataOutputStream, Request request) {
        this.operation.accept(dataOutputStream, request);
    }

    private static void makeResponse(DataOutputStream dataOutputStream, Request request,
        Status status) throws IOException, URISyntaxException {
        responseHeader(dataOutputStream, request, status);
        responseBody(dataOutputStream, request, status);
    }

    private static void responseHeader(DataOutputStream dataOutputStream, Request request,
        Status status) throws IOException, URISyntaxException {
        ContentType contentType = request.getContentType();

        dataOutputStream.writeBytes(
            "HTTP/1.1 "
                + status.getStatusCode()
                + " "
                + status.getStatusName()
                + " \r\n");
        if (Objects.nonNull(contentType)) {
            dataOutputStream
                .writeBytes("Content-Type: " + contentType.getContentTypeValue()
                    + ";charset=utf-8\r\n");
        }
        if (Objects.nonNull(contentType) && status.isNeedBody() && request.getContentType()
            .equals(ContentType.HTML)) {
            byte[] body = FileIoUtils.loadFileFromClasspath(StringUtils.generatePath(request));
            dataOutputStream.writeBytes("Content-Length: " + body.length + "\r\n");
        }
        if (status.isNeedLocation()) {
            dataOutputStream.writeBytes("Location: /index.html" + "\r\n");
        }
        dataOutputStream.writeBytes("\r\n");
    }

    private static void responseBody(DataOutputStream dataOutputStream, Request request,
        Status status) throws IOException, URISyntaxException {
        ContentType contentType = request.getContentType();

        if (!(Objects.nonNull(contentType) && status.isNeedBody() && request.getContentType()
            .equals(ContentType.HTML))) {
            return;
        }
        byte[] body = FileIoUtils.loadFileFromClasspath(StringUtils.generatePath(request));
        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();
    }
}
