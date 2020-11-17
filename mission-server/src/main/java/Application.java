import java.util.Arrays;
import java.util.List;

import controller.CreateUserController;
import controller.ListController;
import controller.RootController;
import controller.UserLoginController;
import domain.controller.StaticController;
import dto.UrlMappingCreateDto;
import util.HandlebarsTemplateEngine;

public class Application {
    public static void main(String[] args) throws Exception {
        List<UrlMappingCreateDto> urlMappingCreateDtos = Arrays.asList(
            UrlMappingCreateDto.of("/", new RootController()),
            UrlMappingCreateDto.of("/user/create", new CreateUserController()),
            UrlMappingCreateDto.of("/user/login", new UserLoginController()),
            UrlMappingCreateDto.of("/user/list", new ListController(HandlebarsTemplateEngine.getInstance()))
        );

        WebServer webServer = new WebServer(new StaticController(), urlMappingCreateDtos);
        webServer.run();
    }
}
