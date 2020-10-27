package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import web.application.FrontController;
import web.application.UrlMapper;
import web.application.controller.Controller;
import web.application.controller.CreateUserController;
import web.application.controller.ListController;
import web.application.controller.RootController;
import web.application.controller.UserLoginController;
import web.application.util.HandlebarsTemplateEngine;
import web.server.domain.request.HttpRequest;
import web.server.domain.response.HttpResponse;
import web.server.dto.UrlMappingCreateDto;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Controller CONTROLLER;

    static {
        List<UrlMappingCreateDto> urlMappingCreateDtos = Arrays.asList(
            UrlMappingCreateDto.of("/", new RootController()),
            UrlMappingCreateDto.of("/user/create", new CreateUserController()),
            UrlMappingCreateDto.of("/user/login", new UserLoginController()),
            UrlMappingCreateDto.of("/user/list", new ListController(HandlebarsTemplateEngine.getInstance()))
        );

        UrlMapper instance = UrlMapper.from(urlMappingCreateDtos);
        CONTROLLER = FrontController.from(instance);
    }

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream();
             BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(in, StandardCharsets.UTF_8)));
             DataOutputStream dataOutputStream = new DataOutputStream(out)) {

            HttpRequest httpRequest = new HttpRequest(bufferedReader);
            HttpResponse httpResponse = new HttpResponse(dataOutputStream);

            CONTROLLER.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
