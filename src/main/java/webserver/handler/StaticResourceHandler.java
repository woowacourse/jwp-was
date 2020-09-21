package webserver.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import utils.FileIoUtils;
import webserver.HttpResponse;
import webserver.ResourceTypeMatcher;

public class StaticResourceHandler extends Handler {
	@Override
	public void handleRequest(String path, DataOutputStream dos, BufferedReader br) throws
		IOException, URISyntaxException {
		ResourceTypeMatcher fileType = ResourceTypeMatcher.findContentType(path);
		byte[] body = FileIoUtils.loadFileFromClasspath(fileType.parseFilePath(path));

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
	public boolean canHandle(String path) {
		return ResourceTypeMatcher.isContainType(path);
	}
}
