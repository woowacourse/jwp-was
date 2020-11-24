package clinet.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import utils.RequestPathUtil;
import web.ContentType;
import web.request.HttpRequest;
import web.response.HttpResponse;

public class ResourceController extends AbstractController {
	@Override
	public boolean canHandle(String path) {
		return ContentType.isContainType(path);
	}

	@Override
	protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
		String filePath = request.getPath();
		String requestPath = RequestPathUtil.extractFilePath(filePath);
		byte[] body = FileIoUtils.loadFileFromClasspath(requestPath);

		response.response200Header(body.length);
		response.responseBody(body);
	}
}
