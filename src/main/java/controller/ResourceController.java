package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import exception.FailedForwardException;
import exception.UnauthorizedRequestException;
import exception.WrongPathException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseGenerator;
import utils.FileIoUtils;
import utils.ResourcePathUtils;

import static http.request.HttpRequestReader.REQUEST_URI;

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
			httpResponse.forward(responseBody, dos);
		} catch (IOException e) {
			throw new FailedForwardException();
		} catch (URISyntaxException e) {
			throw new WrongPathException();
		}
	}
}
