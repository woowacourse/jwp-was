package webserver.handler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.ResourceTypeMatcher;

public class StaticResourceHandler extends Handler {
	@Override
	public void handleRequest(HttpRequest httpRequest, DataOutputStream dos) throws IOException, URISyntaxException {
		ResourceTypeMatcher fileType = ResourceTypeMatcher.findContentType(httpRequest.getUrl());
		byte[] body = FileIoUtils.loadFileFromClasspath(fileType.parseFilePath(httpRequest.getUrl()));

		HttpResponse.response200Header(dos, body.length, fileType.getContentType(), logger);
		responseBody(dos, body);
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public boolean canHandle(String url) {
		return ResourceTypeMatcher.isContainType(url);
	}
}
