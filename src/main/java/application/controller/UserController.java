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
	private static final String LOCATION = "Location";
	private static final String SET_COOKIE = "Set-Cookie";
	private static final String PATH = "Path=/";

	@RequestMapping(path = "/user/create", method = HttpMethod.POST)
	public static void create(HttpRequest request, HttpResponse response) {
		logger.debug("request : {}, request : {}", request, response);

		UserService.create(request);

		logger.debug("Created User: {}", DataBase.findUserById(request.getHttpBodyValueOf("userId")));
		response.addHttpHeader(LOCATION, "/index.html");
		response.setHttpStatus(HttpStatus.FOUND);
	}

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public static void login(HttpRequest request, HttpResponse response) {
        logger.debug("request : {}, request : {}", request, response);

        try {
			String sessionId = UserService.login(request);

			response.addHttpHeader(LOCATION, "/index.html");
			response.addHttpHeader(SET_COOKIE, "sessionId=" + sessionId + "; " + PATH);
			response.setHttpStatus(HttpStatus.FOUND);
		} catch (UserLoginException e) {
			logger.debug("error : {}", e.getMessage());

			response.addHttpHeader(LOCATION, "/user/login_failed.html");
			response.addHttpHeader(SET_COOKIE, "logined=false; " + PATH);
			response.setHttpStatus(HttpStatus.FOUND);
		}
	}

	@RequestMapping(path = "/user/list", method = HttpMethod.GET)
	public static void list(HttpRequest request, HttpResponse response) throws IOException {
		logger.debug("request : {}, request : {}", request, response);

		if (UserService.isLogin(request)) {
			List<UserDto> users = UserService.findAll().stream()
				.map(UserDto::new)
				.collect(Collectors.toList());
			response.setHttpStatus(HttpStatus.OK);
			String body = ViewResolver.resolve("user/list", users);
			response.addHttpBody(body);
		} else {
			response.addHttpHeader(LOCATION, "/user/login.html");
			response.setHttpStatus(HttpStatus.FOUND);
		}
	}
}
