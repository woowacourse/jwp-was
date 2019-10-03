package webserver.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import webserver.exception.FailedForwardException;
import webserver.exception.UnauthorizedRequestException;
import webserver.exception.WrongPathException;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseGenerator;
import webserver.utils.FileIoUtils;
import webserver.utils.ResourcePathUtils;

import static webserver.controller.CreateUserController.JSESSION_ID;
import static webserver.http.request.HttpRequestReader.REQUEST_URI;

public class ResourceController extends AbstractController {
	@Override
	public void doPost(HttpRequest httpRequest, DataOutputStream dos) {
		throw new UnauthorizedRequestException();
	}

	@Override
	public void doGet(HttpRequest httpRequest, DataOutputStream dos) {
		try {
			String path = ResourcePathUtils.getResourcePath(httpRequest.getRequestLineElement(REQUEST_URI));
			byte[] responseBody = FileIoUtils.loadFileFromClasspath(path);
			HttpResponse httpResponse = HttpResponseGenerator.response200Header(
					httpRequest.getRequestLineElement(REQUEST_URI), responseBody.length);

			if (!httpRequest.hasCookieValue(JSESSION_ID)) {
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
}
