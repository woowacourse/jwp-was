package controller;

import java.io.DataOutputStream;
import java.io.IOException;

import exception.FailedRedirectException;
import exception.UnauthorizedRequestException;
import http.response.HttpResponse;
import service.UserService;
import http.response.HttpResponseGenerator;
import http.request.HttpRequest;

public class CreateUserController extends AbstractController {
	@Override
	public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
		try {
			UserService.saveUser(httpRequest.getHttpRequestBody());
			String redirectPath = "http://localhost:8080/index.html";
			HttpResponse httpResponse = new HttpResponse(HttpResponseGenerator.response302Header(redirectPath));
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
