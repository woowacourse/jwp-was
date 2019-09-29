package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import exception.FailedForwardException;
import exception.UnauthorizedRequestException;
import exception.WrongPathException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseGenerator;
import model.User;
import service.UserService;
import utils.FileIoUtils;
import utils.ResourcePathUtils;

import static controller.CreateUserController.JSESSION_ID;
import static http.request.HttpRequestReader.REQUEST_URI;

public class UserListController extends AbstractController {
	@Override
	public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
		throw new UnauthorizedRequestException();
	}

	@Override
	public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
		try {
			byte[] responseBody = getResponseBody(httpRequest);

			HttpResponse httpResponse = HttpResponseGenerator.response200Header(
					httpRequest.getRequestLineElement(REQUEST_URI), responseBody.length);

			if(!httpRequest.isCookieValue(JSESSION_ID)) {
				String uuid = sessionManager.generateInitialSession();
				httpResponse.setInitialSession(uuid);
			}

			httpResponse.forward(responseBody, dos);
		} catch (IOException e) {
			throw new FailedForwardException();
		} catch (URISyntaxException e) {
			throw new WrongPathException();
		}
	}

	private byte[] getResponseBody(HttpRequest httpRequest) throws IOException, URISyntaxException {
		if (httpRequest.isCookieValue("logined")) {
			return generateDynamicResource();
		}
		return FileIoUtils.loadFileFromClasspath(ResourcePathUtils.getResourcePath("/user/login.html"));
	}

	private byte[] generateDynamicResource() throws IOException {
		TemplateLoader loader = new ClassPathTemplateLoader();
		loader.setPrefix("/templates");
		loader.setSuffix(".html");
		Handlebars handlebars = new Handlebars(loader);

		Template template = handlebars.compile("user/list");

		Map<String, List<User>> users = new LinkedHashMap<>();
		users.put("users", UserService.findAllUsers());

		return template.apply(users).getBytes();
	}
}
