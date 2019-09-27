package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import exception.UnauthorizedRequestException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseGenerator;
import service.UserService;
import utils.FileIoUtils;
import utils.ResourcePathUtils;

public class UserLoginController extends AbstractController {
	@Override
	public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
		UserService userService = new UserService();

		boolean result = userService.login(httpRequest.getHttpRequestBody().get("userId"),
				httpRequest.getHttpRequestBody().get("password"));

		try {
			String location = (result) ? "/index.html" : "/user/login_failed.html";
			String path = ResourcePathUtils.getResourcePath(location);
			byte[] responseBody = FileIoUtils.loadFileFromClasspath(path);
			HttpResponse httpResponse = new HttpResponse(HttpResponseGenerator.responseLoginSuccess(path, responseBody.length));
			httpResponse.forward(responseBody, dos);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
		throw new UnauthorizedRequestException();
	}
}
