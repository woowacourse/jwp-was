package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;

import webserver.exception.FailedRedirectException;
import webserver.exception.UnauthorizedRequestException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;
import application.service.UserService;

public class CreateUserController extends AbstractController {
	public static final String JSESSION_ID = "jsessionId";

	@Override
	public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
		try {
			UserService.saveUser(httpRequest.getHttpRequestBody());
			String redirectPath = "/index.html";
			HttpResponse httpResponse = HttpResponseGenerator.response302Header(redirectPath);

			if(!httpRequest.hasCookieValue(JSESSION_ID)) {
				String uuid = sessionManager.generateInitialSession();
				httpResponse.setInitialSession(uuid);
			}

			httpResponse.sendRedirect(dos);
		} catch (IOException e) {
			throw new FailedRedirectException();
		}
	}

	@Override
	public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
		throw new UnauthorizedRequestException();
	}
}
