package application.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.db.DataBase;
import application.model.UserDto;
import application.model.UserLoginException;
import application.service.UserService;
import application.view.ViewResolver;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public static void create(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}, request : {}", request, response);
        UserService.create(request);

        logger.debug("Created User: {}", DataBase.findUserById(request.getHttpBodyValueOf("userId")));
        response.addHttpHeader("Location", "/index.html");
        response.setHttpStatus(HttpStatus.FOUND);
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public static void login(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}, request : {}", request, response);
        try {
            UserService.login(request);
            response.addHttpHeader("Location", "/index.html");
			response.addHttpHeader("Set-Cookie", "logined=true; Path=/");
			response.setHttpStatus(HttpStatus.FOUND);
		} catch (UserLoginException e) {
			logger.debug("error : {}", e.getMessage());

			response.addHttpHeader("Location", "/user/login_failed.html");
			response.addHttpHeader("Set-Cookie", "logined=false; Path=/");
			response.setHttpStatus(HttpStatus.FOUND);
		}
	}

	@RequestMapping(path = "/user/list", method = HttpMethod.GET)
	public static void list(HttpRequest request, HttpResponse response) throws IOException {
		logger.debug("request : {}, request : {}", request, response);

		String cookie = request.getHttpHeaderParameterOf("Cookie");
		if (cookie.contains("logined=true")) {
			List<UserDto> users = UserService.findAll().stream()
				.map(UserDto::new)
				.collect(Collectors.toList());
			response.setHttpStatus(HttpStatus.OK);
			String body = ViewResolver.resolve("user/list", users);
			response.addHttpBody(body);
		} else {
			response.addHttpHeader("Location", "/user/login.html");
			response.addHttpHeader("Set-Cookie", "logined=false; Path=/");
			response.setHttpStatus(HttpStatus.FOUND);
		}
	}
}
