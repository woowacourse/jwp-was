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

import application.Controller;
import controller.CreateUserController;
import controller.ListController;
import controller.RootController;
import controller.UserLoginController;
import domain.request.HttpServletRequest;
import domain.response.HttpServletResponse;
import dto.UrlMappingCreateDto;
import servlet.HttpRequest;
import servlet.HttpResponse;
import util.HandlebarsTemplateEngine;

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

            HttpRequest httpRequest = new HttpServletRequest(bufferedReader);
            HttpResponse httpResponse = new HttpServletResponse(dataOutputStream);

            CONTROLLER.service(httpRequest, httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
